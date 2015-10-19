package hadoop;

import org.apache.hadoop.mapreduce.v2.app.job.event.TaskEventType;
import org.apache.hadoop.yarn.event.AbstractEvent;

/**
 * Created by ericson on 2015/3/22 0022.
 */
public class TaskEvent extends AbstractEvent<TaskEventType> {
    private String taskID;

    public TaskEvent(String taskID, TaskEventType type) {
        super(type);
        this.taskID = taskID;
    }

    public String getTaskID() {
        return taskID;
    }
}


