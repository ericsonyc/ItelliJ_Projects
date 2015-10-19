package spark

import org.apache.spark.Partitioner

/**
 * Created by jtang on 2015/1/16 0016.
 */
class ExtractPartition[V](
                           partitions: Int,
                           elements: Int)
  extends Partitioner {


  override def numPartitions: Int = partitions

  def getPartition(key: Any): Int = {
    val k = key.asInstanceOf[Int]
    // `k` is assumed to go continuously from 0 to elements-1.
    return k * partitions / elements
  }
}
