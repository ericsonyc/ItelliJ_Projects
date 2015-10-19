package exercise

import scala.math._

/**
 * Created by ericson on 2014/12/23 0023.
 */
object TestMapView {
  def main(args: Array[String]) {
    var start = System.currentTimeMillis()
    (0 to 1000).map(pow(10, _)).map(1 / _)
    var end = System.currentTimeMillis()
    println("Time:" + (end - start) + "ms")
    start = System.currentTimeMillis()
    (0 to 1000).view.map(pow(10, _)).map(1 / _).force
    end = System.currentTimeMillis()
    println("Time:" + (end - start) + "ms")

    var count = 0
    for (c <- (0 until 100).par) {
      if (c % 2 == 0) count += 1
    }
    println(count)
  }
}
