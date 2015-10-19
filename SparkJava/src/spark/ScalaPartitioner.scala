package spark

import org.apache.spark.{HashPartitioner, Partitioner}

/**
 * Created by jtang on 2015/1/16 0016.
 */
class ScalaPartitioner(partitions: Int) extends Partitioner {
  def numPartitions = partitions

  def getPartition(key: Any): Int = key match {
    case null => 0
    case _ => {
      val rawMod = key.## / numPartitions
      println("rawMod:"+rawMod)
      if (rawMod > 0) numPartitions else 0
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