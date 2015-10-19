package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.v2.app.job.event.JobEventType;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

/**
 * Created by ericson on 2015/3/22 0022.
 */
public class SimpleMRAppMasterTest {
    public static void main(String[] args) throws Exception {
        System.out.println("begin main");
        String jobID = "job_20140322_01";
        System.out.println("get the String jobID");
        SimpleMRAppMaster appMaster = new SimpleMRAppMaster("Simple MRAppMaster", jobID, 5);
        System.out.println("get the SimpleMRAppMaster object");
        YarnConfiguration conf = new YarnConfiguration(new Configuration());
        System.out.println("get yarnconfiguration");
        appMaster.serviceInit(conf);
        System.out.println("Service conf");
        appMaster.serviceStart();
        System.out.println("Service Start");
        appMaster.getDispatcher().getEventHandler().handle(new JobEvent(jobID, JobEventType.JOB_INIT));
        System.out.println("JOB_KILL");
        appMaster.getDispatcher().getEventHandler().handle(new JobEvent(jobID, JobEventType.JOB_START));
        System.out.println("JOB_INIT");
        appMaster.getDispatcher().getEventHandler().handle(new JobEvent(jobID,JobEventType.JOB_ABORT_COMPLETED));
        //appMaster.serviceStop();
    }
}
