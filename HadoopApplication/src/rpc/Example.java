package rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationResponse;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericson on 2015/3/28 0028.
 */
public class Example {
    public static void main(String[] args) throws Exception {
        Configuration conf=new Configuration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(conf);
        yarnClient.start();
        YarnClientApplication app=yarnClient.createApplication();
        GetNewApplicationResponse appResponse=app.getNewApplicationResponse();
        ApplicationSubmissionContext appContext=app.getApplicationSubmissionContext();
        ApplicationId appId=appContext.getApplicationId();
        appContext.setKeepContainersAcrossApplicationAttempts(true);
        appContext.setApplicationName("Client");
        Map<String,LocalResource> localResources=new HashMap<String,LocalResource>();
        FileSystem fs=FileSystem.get(conf);
        
    }
}
