package hadoop;

import org.apache.hadoop.mapreduce.v2.app.job.JobStateInternal;
import org.apache.hadoop.mapreduce.v2.app.job.event.JobEventType;
import org.apache.hadoop.yarn.event.EventHandler;
import org.apache.hadoop.yarn.state.*;

import java.util.EnumSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by ericson on 2015/3/22 0022.
 */
public class JobStateMachine implements EventHandler<JobEvent> {
    private String jobID;
    private EventHandler eventHandler;
    private Lock writeLock;
    private Lock readLock;

    protected static final StateMachineFactory<JobStateMachine, JobStateInternal, JobEventType, JobEvent> stateMachineFactory = new StateMachineFactory<JobStateMachine, JobStateInternal, JobEventType, JobEvent>(JobStateInternal.NEW).addTransition(JobStateInternal.NEW, JobStateInternal.INITED, JobEventType.JOB_INIT, new InitTransition()).addTransition(JobStateInternal.INITED, JobStateInternal.SETUP, JobEventType.JOB_START, new StartTransition()).addTransition(JobStateInternal.SETUP, JobStateInternal.RUNNING, JobEventType.JOB_SETUP_COMPLETED, new SetupCompletedTransition()).addTransition(JobStateInternal.RUNNING, EnumSet.of(JobStateInternal.KILLED, JobStateInternal.SUCCEEDED), JobEventType.JOB_COMPLETED, new JobTasksCompletedTransition()).installTopology();

    private StateMachine<JobStateInternal, JobEventType, JobEvent> stateMachine;

    public JobStateMachine(String jobID, EventHandler eventHandler) {
        this.jobID = jobID;
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
        this.eventHandler = eventHandler;
        stateMachine = stateMachineFactory.make(this);
    }

    protected StateMachine<JobStateInternal, JobEventType, JobEvent> getStateMachine() {
        return stateMachine;
    }

    public String getJobID() {
        return jobID;
    }

    public static class InitTransition implements SingleArcTransition<JobStateMachine, JobEvent> {
        @Override
        public void transition(JobStateMachine jobStateMachine, JobEvent jobEvent) {
            System.out.println("Receiving event " + jobEvent);
            jobStateMachine.eventHandler.handle(new JobEvent(jobStateMachine.getJobID(), JobEventType.JOB_INIT));
        }
    }

    public static class StartTransition implements SingleArcTransition<JobStateMachine, JobEvent> {
        @Override
        public void transition(JobStateMachine jobStateMachine, JobEvent jobEvent) {
            System.out.println("Receiving event " + jobEvent);
            jobStateMachine.eventHandler.handle(new JobEvent(jobStateMachine.getJobID(), JobEventType.JOB_START));
        }
    }

    public static class SetupCompletedTransition implements SingleArcTransition<JobStateMachine, JobEvent> {
        @Override
        public void transition(JobStateMachine jobStateMachine, JobEvent jobEvent) {
            System.out.println("Receiving event " + jobEvent);
            jobStateMachine.eventHandler.handle(new JobEvent(jobStateMachine.getJobID(), JobEventType.JOB_SETUP_COMPLETED));
        }
    }

    public static class JobTasksCompletedTransition implements MultipleArcTransition<JobStateMachine, JobEvent, JobStateInternal> {
        @Override
        public JobStateInternal transition(JobStateMachine jobStateMachine, JobEvent jobEvent) {
            System.out.println("Receiving event " + jobEvent);
            jobStateMachine.eventHandler.handle(new JobEvent(jobStateMachine.getJobID(), JobEventType.JOB_COMPLETED));
            return JobStateInternal.SUCCEEDED;
        }
    }

    @Override
    public void handle(JobEvent jobEvent) {
        try {
            writeLock.lock();
            JobStateInternal oldState = getInternalState();
            try {
                System.out.println("handle jobevent before doTransition");
                getStateMachine().doTransition(jobEvent.getType(), jobEvent);
                System.out.println("handle jobevent after doTransition");
            } catch (InvalidStateTransitonException e) {
                System.out.println("Can't handle this event at current state");
            }
            if (oldState != getInternalState()) {
                System.out.println("Job Transitioned from " + oldState + " to " + getInternalState());
            }
        } finally {
            writeLock.unlock();
        }
    }

    public JobStateInternal getInternalState() {
        readLock.lock();
        try {
            return getStateMachine().getCurrentState();
        } finally {
            readLock.unlock();
        }
    }
}
