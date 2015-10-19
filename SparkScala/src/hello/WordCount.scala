package hello

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by ericson on 2014/12/29 0029.
 */
object WordCount {
  def main(args: Array[String]) {
    if (args.length == 0) {
      System.err.println("Usage:WordCount");
      System.exit(1);
    }
    val conf = new SparkConf().setAppName("WordCount")
    val sc = new SparkContext(conf)
    sc.textFile(args(0)).flatMap(_.split(" ")).map(println _)
    sc.stop()
  }
}
