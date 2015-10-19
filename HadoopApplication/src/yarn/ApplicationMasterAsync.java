package yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.AMRMClient;
import org.apache.hadoop.yarn.client.api.NMClient;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.util.Records;

import java.util.Collections;
import java.util.List;

/**
 * Created by ericson on 2015/3/29 0029.
 */
public class ApplicationMasterAsync implements AMRMClientAsync.CallbackHandler {
    Configuration configuration;
    NMClient nmClient;
    String command;
    int numContainersToWaitFor;

    public ApplicationMasterAsync(String command, int numContainersToWaitFor) {
        this.command = command;
        configuration = new YarnConfiguration();
        this.numContainersToWaitFor = numContainersToWaitFor;
        nmClient = NMClient.createNMClient();
        nmClient.init(configuration);
        nmClient.start();
    }

    @Override
    public void onContainersCompleted(List<ContainerStatus> list) {
        for (ContainerStatus status : list) {
            System.out.println("[AM] Completed container " + status.getContainerId());
            synchronized (this) {
                numContainersToWaitFor--;
            }
        }
    }

    @Override
    public void onContainersAllocated(List<Container> list) {
        for (Container container : list) {
            try {
                ContainerLaunchContext ctx = Records.newRecord(ContainerLaunchContext.class);
                ctx.setCommands(Collections.singletonList(command + " 1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout" + " 2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr"));
                System.out.println("[AM] Launching container " + container.getId());
                nmClient.startContainer(container, ctx);
            } catch (Exception ex) {
                System.err.println("[AM] Error launching container " + container.getId() + " " + ex);
            }
        }
    }

    @Override
    public void onShutdownRequest() {

    }

    @Override
    public void onNodesUpdated(List<NodeReport> list) {

    }

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public void onError(Throwable throwable) {

    }

    public boolean doneWithContainers() {
        return numContainersToWaitFor == 0;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public static void main(String[] args) throws Exception {
        final String command = args[0];
        final int n = Integer.valueOf(args[1]);
        ApplicationMasterAsync master = new ApplicationMasterAsync(command, n);
        master.runMainLoop();
    }

    public void runMainLoop() throws Exception {
        AMRMClientAsync<AMRMClient.ContainerRequest> rmClient = AMRMClientAsync.createAMRMClientAsync(100, this);
        rmClient.init(getConfiguration());
        rmClient.start();
        System.out.println("[AM] registerApplicationMaster 0");
        rmClient.registerApplicationMaster("", 0, "");
        System.out.println("[AM] registerApplicationMaster 1");
        Priority priority = Records.newRecord(Priority.class);
        priority.setPriority(0);
        Resource capability = Records.newRecord(Resource.class);
        capability.setMemory(128);
        capability.setVirtualCores(1);
        for (int i = 0; i < numContainersToWaitFor; i++) {
            AMRMClient.ContainerRequest containerAsk=new AMRMClient.ContainerRequest(capability,null,null,priority);
            System.out.println("[AM] Making res-req "+i);
            rmClient.addContainerRequest(containerAsk);
        }
        System.out.println("[AM] unregisterApplicationMaster 0");
        rmClient.unregisterApplicationMaster(FinalApplicationStatus.SUCCEEDED,"","");
        System.out.println("[AM] unregisterApplicationMaster 1");
    }
}
