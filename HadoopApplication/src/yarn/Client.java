package yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.util.Apps;
import org.apache.hadoop.yarn.util.ConverterUtils;
import org.apache.hadoop.yarn.util.Records;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericson on 2015/3/28 0028.
 */
public class Client {
    Configuration conf = new YarnConfiguration();

    public void run(String[] args) throws Exception {
        final String command = args[0];
        final int n = Integer.valueOf(args[1]);
        final Path jarPath = new Path(args[2]);
        YarnConfiguration conf = new YarnConfiguration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();

        YarnClientApplication app = yarnClient.createApplication();
        ContainerLaunchContext amContainer = Records.newRecord(ContainerLaunchContext.class);
        amContainer.setCommands(Collections.singletonList("$JAVA_HOME/bin/java" + " -Xmx256M" + " yarn.ApplicationMaster " + command + " " + String.valueOf(n) + " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout" + " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"));
        LocalResource appMasterJar = Records.newRecord(LocalResource.class);
        setupAppMasterJar(jarPath, appMasterJar);
        amContainer.setLocalResources(Collections.singletonMap("HadoopApplication.jar", appMasterJar));
        Map<String, String> appMasterEnv = new HashMap<String, String>();
        setupAppMasterEnv(appMasterEnv);
        amContainer.setEnvironment(appMasterEnv);
        Resource capability = Records.newRecord(Resource.class);
        capability.setMemory(256);
        capability.setVirtualCores(1);
        ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
        appContext.setApplicationName("simple-yarn-app");
        appContext.setAMContainerSpec(amContainer);
        appContext.setResource(capability);
        appContext.setQueue("default");

        ApplicationId appId = appContext.getApplicationId();
        System.out.println("Submiting application " + appId);
        yarnClient.submitApplication(appContext);

        ApplicationReport appReport = yarnClient.getApplicationReport(appId);
        YarnApplicationState appState = appReport.getYarnApplicationState();
        while (appState != YarnApplicationState.FINISHED && appState != YarnApplicationState.KILLED && appState != YarnApplicationState.FAILED) {
            Thread.sleep(100);
            appReport = yarnClient.getApplicationReport(appId);
            appState = appReport.getYarnApplicationState();
        }
        System.out.println("Application " + appId + " finished with" + " state" + appState + " at" + appReport.getFinishTime());
    }

    private void setupAppMasterJar(Path jarPath, LocalResource appMasterJar) throws Exception {
        FileStatus jarStat = FileSystem.get(conf).getFileStatus(jarPath);
        appMasterJar.setResource(ConverterUtils.getYarnUrlFromPath(jarPath));
        appMasterJar.setSize(jarStat.getLen());
        appMasterJar.setTimestamp(jarStat.getModificationTime());
        appMasterJar.setType(LocalResourceType.FILE);
        appMasterJar.setVisibility(LocalResourceVisibility.PUBLIC);
    }

    private void setupAppMasterEnv(Map<String, String> appMasterEnv) {
        for (String c : conf.getStrings(YarnConfiguration.YARN_APPLICATION_CLASSPATH, YarnConfiguration.DEFAULT_YARN_APPLICATION_CLASSPATH)) {
            Apps.addToEnvironment(appMasterEnv, ApplicationConstants.Environment.CLASSPATH.name(), c.trim());
        }
        Apps.addToEnvironment(appMasterEnv, ApplicationConstants.Environment.CLASSPATH.name(), ApplicationConstants.Environment.PWD.$() + File.separator + "*");
    }

    public static void main(String[] args) throws Exception {
        Client c=new Client();
        c.run(args);
    }
}
