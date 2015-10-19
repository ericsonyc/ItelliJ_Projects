package yarn;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationResponse;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.util.ConverterUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericson on 2015/4/27 0027.
 */
public class Client {

    private final static Log LOG = LogFactory.getLog(Client.class);

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        //init yarnClient
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();

        //get application id
        YarnClientApplication app = yarnClient.createApplication();
        GetNewApplicationResponse appResponse = app.getNewApplicationResponse();
        ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
        ApplicationId appId = appContext.getApplicationId();

        appContext.setKeepContainersAcrossApplicationAttempts(true);
        appContext.setApplicationName("Client");
        Map<String, LocalResource> localResources = new HashMap<String, LocalResource>();
        LOG.info("Copy App Master jar from local filesystem and add to local environment");
        FileSystem fs = FileSystem.get(conf);
        addToLocalResources(fs,appMasterJar)
    }
}
