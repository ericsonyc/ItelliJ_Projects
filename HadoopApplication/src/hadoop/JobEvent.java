package hadoop;

import org.apache.hadoop.mapreduce.v2.app.job.event.JobEventType;
import org.apache.hadoop.yarn.event.AbstractEvent;

/**
 * Created by ericson on 2015/3/22 0022.
 */
public class JobEvent extends AbstractEvent<JobEventType> {
    private String jobID;

    public JobEvent(String jobID, JobEventType type) {
        super(type);
        this.jobID = jobID;
    }

    public String getJobID() {
        return jobID;
    }
}
