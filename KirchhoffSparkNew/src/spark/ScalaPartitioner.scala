package spark

import org.apache.spark.{HashPartitioner, Partitioner}

/**
 * Created by jtang on 2015/1/16 0016.
 */
class ScalaPartitioner(partitions: Int, elemens: Long) extends Partitioner {
  def numPartitions = partitions

  def getPartition(key: Any): Int = key match {
    case null => 0
    case _ => {
      val rawMod = elemens / partitions
      //println("rawMod:" + key.##)
      var index = key.## / rawMod
      if (index >= partitions) {
        index = partitions - 1
      }
      //println("index:" + index)
      index.toInt
    }
  }

  override def equals(other: Any): Boolean = other match {
    case h: HashPartitioner =>
      h.numPartitions == numPartitions
    case _ =>
      false
  }

  override def hashCode: Int = numPartitions
}