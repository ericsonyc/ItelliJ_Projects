package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.map.WrappedMapper;
import org.apache.hadoop.mapreduce.task.MapContextImpl;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jtang on 2015/1/12 0012.
 */
public class MultiMap<K1, V1, K2, V2>
        extends Mapper<K1, V1, K2, V2> {


    public static String NUM_THREADS = "mapreduce.mapper.multithreadedmapper.threads";
    public static String MAP_CLASS = "mapreduce.mapper.multithreadedmapper.mapclass";
    private Class<? extends Mapper<K1, V1, K2, V2>> mapClass;
    private Context outer;
    private List<MapRunner> runners;

    /**
     * The number of threads in the thread pool that will run the map function.
     *
     * @param job the job
     * @return the number of threads
     */
    public static int getNumberOfThreads(JobContext job) {
        System.out.println("getNumberOfThreads");
        return job.getConfiguration().getInt(NUM_THREADS, 1);
    }

    /**
     * Set the number of threads in the pool for running maps.
     *
     * @param job     the job to modify
     * @param threads the new number of threads
     */
    public static void setNumberOfThreads(Job job, int threads) {
        System.out.println("setNumberOfThreads");
        job.getConfiguration().setInt(NUM_THREADS, threads);
    }

    /**
     * Get the application's mapper class.
     *
     * @param <K1> the map's input key type
     * @param <V1> the map's input value type
     * @param <K2> the map's output key type
     * @param <V2> the map's output value type
     * @param job  the job
     * @return the mapper class to run
     */
    @SuppressWarnings("unchecked")
    public static <K1, V1, K2, V2>
    Class<Mapper<K1, V1, K2, V2>> getMapperClass(JobContext job) {
        System.out.println("getMapperClass");
        return (Class<Mapper<K1, V1, K2, V2>>)
                job.getConfiguration().getClass(MAP_CLASS, Mapper.class);
    }

    public static <K1, V1, K2, V2>
    void setMapperClass(Job job,
                        Class<? extends Mapper<K1, V1, K2, V2>> cls) {
        System.out.println("setMapperClass");
        if (MultiMap.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Can't have recursive " +
                    "MultithreadedMapper instances.");
        }
        job.getConfiguration().setClass(MAP_CLASS, cls, Mapper.class);
    }

    /**
     * Run the application's maps using a thread pool.
     */
    @Override
    public void run(Context context) throws IOException, InterruptedException {
        System.out.println("run");
        outer = context;
        int numberOfThreads = getNumberOfThreads(context);
        mapClass = getMapperClass(context);
//        System.out.println("numberofthreads:" + numberOfThreads);
        System.out.println("mapclass:" + mapClass.getName());


        runners = new ArrayList<MapRunner>(numberOfThreads);
        System.out.println("numberOfThreads:" + numberOfThreads);
        for (int i = 0; i < numberOfThreads; ++i) {
            System.out.println("i" + i);
            MapRunner thread = new MapRunner(context, numberOfThreads, i);
            thread.start();
            runners.add(i, thread);
        }
        for (int i = 0; i < numberOfThreads; ++i) {
            MapRunner thread = runners.get(i);
            thread.join();
            System.out.println("thread over");
            Throwable th = thread.throwable;
            if (th != null) {
                System.out.println("thread throwable");
                if (th instanceof IOException) {
                    throw (IOException) th;
                } else if (th instanceof InterruptedException) {
                    throw (InterruptedException) th;
                } else {
                    throw new RuntimeException(th);
                }
            }
        }
    }


    private class SubMapRecordReader extends RecordReader<K1, V1> {
        private K1 key;
        private V1 value;
        private Configuration conf;

        @Override
        public void close() throws IOException {
            System.out.println("reader close");
        }

        @Override
        public float getProgress() throws IOException, InterruptedException {
            System.out.println("reader getProgress");
            return 0;
        }

        @Override
        public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
            System.out.println("reader initalize");
            conf = context.getConfiguration();
        }


        @Override
        public boolean nextKeyValue() throws IOException, InterruptedException {
            System.out.println("reader nextKeyValue");
            synchronized (outer) {
                if (!outer.nextKeyValue()) {
                    return false;
                }
                key = ReflectionUtils.copy(outer.getConfiguration(),
                        outer.getCurrentKey(), key);
                System.out.println("key:" + key);
                value = ReflectionUtils.copy(conf, outer.getCurrentValue(), value);
                return true;
            }
        }

        public K1 getCurrentKey() {
            System.out.println("reader getCurrentKey");
            return key;
        }

        @Override
        public V1 getCurrentValue() {
            System.out.println("reader getCurrentValue");
            return value;
        }
    }

    private class SubMapRecordWriter extends RecordWriter<K2, V2> {

        @Override
        public void close(TaskAttemptContext context) throws IOException,
                InterruptedException {
            System.out.println("writer close");
        }

        @Override
        public void write(K2 key, V2 value) throws IOException,
                InterruptedException {
            System.out.println("writer write");
            synchronized (outer) {
                outer.write(key, value);
            }
        }
    }

    private class SubMapStatusReporter extends StatusReporter {

        @Override
        public float getProgress() {
            System.out.println("reporter getProgress");
            return outer.getProgress();
        }

        @Override
        public Counter getCounter(Enum<?> name) {
            System.out.println("reporter getCounter one");
            return outer.getCounter(name);
        }

        @Override
        public Counter getCounter(String group, String name) {
            System.out.println("reporter getCounter two");
            return outer.getCounter(group, name);
        }

        @Override
        public void progress() {
            System.out.println("reporter progress");
            outer.progress();
        }

        @Override
        public void setStatus(String status) {
            System.out.println("reporter setStatus");
            outer.setStatus(status);
        }

    }

    private class MapRunner extends Thread {
        private Mapper<K1, V1, K2, V2> mapper;
        private Context subcontext;
        private Throwable throwable;
        private RecordReader<K1, V1> reader = new SubMapRecordReader();
        private int numberOfthreads;
        private int index;

        MapRunner(Context context, int numberOfthreads, int index) throws IOException, InterruptedException {
            System.out.println("MapRunner");
            this.numberOfthreads = numberOfthreads;
            this.index = index;
            mapper = ReflectionUtils.newInstance(mapClass, context.getConfiguration());
            MapContext<K1, V1, K2, V2> mapContext =
                    new MapContextImpl<K1, V1, K2, V2>(outer.getConfiguration(),
                            outer.getTaskAttemptID(),
                            reader,
                            new SubMapRecordWriter(),
                            context.getOutputCommitter(),
                            new SubMapStatusReporter(),
                            outer.getInputSplit());
            subcontext = new WrappedMapper<K1, V1, K2, V2>().getMapContext(mapContext);
            //subcontext = context;
            reader.initialize(context.getInputSplit(), context);
        }

        @Override
        public void run() {
            try {
                System.out.println("runthread");
//                Configuration conf = subcontext.getConfiguration();
//                conf.setInt("ThreadNumbers", numberOfthreads);
//                conf.setInt("Index", index);
                mapper.run(subcontext);
                reader.close();
            } catch (Throwable ie) {
                throwable = ie;
            }
        }
    }
}