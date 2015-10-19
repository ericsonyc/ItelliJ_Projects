package exercise

import scala.collection.JavaConversions.propertiesAsScalaMap

/**
 * Created by ericson on 2014/12/24 0024.
 */
object ScalaPatternTest {
  def main(args: Array[String]) {
    var sign = +1
    val ch: Char = sign.toString.charAt(0)
    ch match {
      case '+' => sign = 1
      case '-' => sign = -1
      case _ => sign = 0
    }
    println(sign.toString)
    sign = ch match {
      case '+' => 1
      case '-' => -1
      case _ if Character.isDigit(ch) => Character.digit(ch, 10)
      case _ => 0
    }

    val arr = Array(0, 3, 5, 6, 7)
    val str = arr match {
      case Array(0) => "0"
      case Array(x, y) => x + " " + y
      case Array(0, _*) => "0 ..."
      case _ => "Something else"
    }
    println(str)

    val list = List(0, 4, 34)
    val stt = list match {
      case 0 :: Nil => "0"
      case x :: y :: Nil => x + " " + y
      case 0 :: tail => "0 ..."
      case _ => "Something else"
    }
    println(stt)

    println(Array.unapplySeq(arr))

    val pattern = "([0-9]+)(\\s+)([a-z]+)".r
    val pp = "99    bottles" match {
      case pattern(num, item1, item2) => "yes"
      case _ => "error"
    }
    println(pp)

    for ((k, v) <- System.getProperties) {
      println(k + " " + v)
    }
  }
}
