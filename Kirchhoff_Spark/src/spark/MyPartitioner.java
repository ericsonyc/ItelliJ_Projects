package spark;

import org.apache.spark.Partitioner;

/**
 * Created by ericson on 2016/4/17 0017.
 */
public class MyPartitioner extends Partitioner {

    int numPartitions = 0;
    int elements = 0;

    public MyPartitioner(int partitions, int elements) {
        numPartitions = partitions;
        this.elements = elements;
    }

    public MyPartitioner() {
    }

    @Override
    public int numPartitions() {
        return numPartitions;
    }

    @Override
    public int getPartition(Object o) {
        if (o == null)
            return 0;
        Integer value = (Integer) o;
        int rawMod = elements / this.numPartitions;
        int index = 0;
        if (rawMod != 0) {
            index = value.intValue() / rawMod;
            if (index >= numPartitions) {
                index = numPartitions - 1;
            }
        } else {
            index = value.intValue();
        }
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyPartitioner that = (MyPartitioner) o;

        return numPartitions == that.numPartitions;

    }

    @Override
    public int hashCode() {
        return numPartitions;
    }
}
