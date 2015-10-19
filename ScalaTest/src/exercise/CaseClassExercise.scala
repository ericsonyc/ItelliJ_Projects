package exercise

/**
 * Created by ericson on 2014/12/24 0024.
 */
object CaseClassExercise {
  def main(args: Array[String]) {
    val amt=Dollar(3)
    val price=amt.copy()
    println(price)
  }
}

abstract class Amount

case class Dollar(value: Double) extends Amount

case class Currency(value: Double, unit: String) extends Amount

case object Nothing extends Amount
