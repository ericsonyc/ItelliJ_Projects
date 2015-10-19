package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.v2.app.job.event.JobEventType;
import org.apache.hadoop.mapreduce.v2.app.job.event.TaskEventType;
import org.apache.hadoop.service.CompositeService;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.event.AsyncDispatcher;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.event.EventHandler;

/**
 * Created by ericson on 2015/3/22 0022.
 */
public class SimpleMRAppMaster extends CompositeService {
    private Dispatcher dispatcher;
    private String jobID;
    private int taskNumber;
    private String[] taskIds;

    public SimpleMRAppMaster(String name, String jobID, int taskNumber) {
        super(name);
        this.jobID = jobID;
        this.taskNumber = taskNumber;
        taskIds = new String[taskNumber];
        for (int i = 0; i < taskNumber; i++) {
            taskIds[i] = new String(jobID + "_task_" + i);
        }
    }

    @Override
    protected void serviceStart() throws Exception {
        super.serviceStart();
    }

    @Override
    protected void serviceInit(Configuration conf) throws Exception {
        dispatcher = new AsyncDispatcher();//定义一个中央异步调度器
        System.out.println("begin dispatcher");
        dispatcher.register(JobEventType.class, new JobStateMachine(this.jobID, new JobEventDispatcher()));
        dispatcher.register(TaskEventType.class, new TaskEventDispatcher());
        System.out.println("register event");
        addService((Service) dispatcher);
        System.out.println("addService");
        super.serviceInit(conf);
    }

    @Override
    protected void serviceStop() throws Exception {
        super.serviceStop();
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    private class JobEventDispatcher implements EventHandler<JobEvent> {
        @Override
        public void handle(JobEvent jobEvent) {
            if (jobEvent.getType() == JobEventType.JOB_KILL) {
                System.out.println("Receive JOB_KILL event,killing all the tasks");
                for (int i = 0; i < taskNumber; i++) {
                    dispatcher.getEventHandler().handle(new TaskEvent(taskIds[i], TaskEventType.T_KILL));
                }
            } else if (jobEvent.getType() == JobEventType.JOB_INIT) {
                System.out.println("Receive JOB_INIT event,scheduling tasks");
                for (int i = 0; i < taskNumber; i++) {
                    dispatcher.getEventHandler().handle(new TaskEvent(taskIds[i], TaskEventType.T_SCHEDULE));
                }
            }
        }
    }

    private class TaskEventDispatcher implements EventHandler<TaskEvent> {
        @Override
        public void handle(TaskEvent taskEvent) {
            if (taskEvent.getType() == TaskEventType.T_KILL) {
                System.out.println("Receive T_KILL event of task " + taskEvent.getTaskID());
            } else if (taskEvent.getType() == TaskEventType.T_SCHEDULE) {
                System.out.println("Receive T_SCHEDULE event of task " + taskEvent.getTaskID());
            }
        }
    }
}
