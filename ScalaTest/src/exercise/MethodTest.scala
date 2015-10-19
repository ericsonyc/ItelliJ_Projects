package exercise

import java.awt.event.{ActionEvent, ActionListener}
import javax.swing.JButton

import scala.math._

/**
 * Created by ericson on 2014/12/23 0023.
 */
object MethodTest {
  def main(args: Array[String]) {
    val num = 3.14
    val fun = ceil _
    println(fun(num))
    val array = Array(34.2, 454.2, 54645.6).map(fun)
    val iter = array.iterator
    while (iter.hasNext) {
      println(iter.next())
    }

    val triple = (x: Double) => 3 * x
    println(triple(3))

    println(valueAtOneQuarter(ceil _))
    println(valueAtOneQuarter(sqrt _))

    val qunituple = mulBy(5)
    println(qunituple(20))
    println((1 to 9).map(0.1 * _))

    (1 to 9).map("*" * _).foreach(println(_))
    (1 to 9).filter(_ % 2 == 0).foreach(println _)
    println((1 to 9).reduceLeft(_ * _))
    "Mary has a little lamb".split(" ").sortWith(_.length < _.length).foreach(println(_))
    var counter = 0
    val button = new JButton("Increment")
    button.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent) {
        counter += 1
      }
    })
    println(mulOneAtTime(7)(3))
    println(mulOneAtTime2(2)(10))

    val a = Array("Hello", "World")
    val b = Array("hello", "world", "Hello")
    println(a.corresponds(b)(_.equalsIgnoreCase(_)))
    //    runInThread {
    //      println("Hi");
    //      Thread.sleep(1000);
    //      println("Bye")
    //    }
    //
    //    var x = 10
    //    until(x == 0) {
    //      x -= 1
    //      println(x)
    //    }
    //println(indexOf("Hello", 'e'))
    var set = digits(234)
    set += 34
    val ites = set.iterator
    while (ites.hasNext) {
      println(ites.next())
    }

    val lst = List(1, 4, 6, 7, 8, 98)
    println(sum1(lst))
  }

  def sum1(lst: List[Int]): Int = lst match {
    case Nil => 0
    case h :: t => h + sum(t)
  }

  def sum(lst: List[Int]): Int = if (lst == Nil) 0
  else lst.head + sum(lst.tail)

  def digits(n: Int): Set[Int] = {
    if (n < 0) digits(-n)
    else if (n < 10) Set(n)
    else digits(n / 10) + (n % 10)
  }

  def indexOf(str: String, ch: Char): Int = {
    var i = -1
    try {
      until(i == str.length) {
        try {
          if (str(i) == ch) return i
        } catch {
          case e: Exception => e.printStackTrace()
        }

        i += 1
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }

    return -1
  }

  def until(condition: => Boolean)(block: => Unit) {
    if (!condition) {
      block
      until(condition)(block)
    }
  }

  def runInThread(block: => Unit): Unit = {
    new Thread() {
      override def run() {
        block
      }
    }.start()
  }

  def mulOneAtTime2(x: Int)(y: Int) = x * y

  def mul(x: Int, y: Int) = x * y

  def mulOneAtTime(x: Int) = (y: Int) => x * y

  def mulBy(factor: Double) = (x: Double) => factor * x

  def valueAtOneQuarter(f: (Double) => Double) = f(0.25)
}
