package exercise

/**
 * Created by ericson on 2014/12/30 0030.
 */
object Parameter {
  def main(args: Array[String]) {
    val pair = new Pair("hello", "hello")
    pair.getMiddle(Array("hello", "hh"))
    val pairTo = new PairTo("Fred", "Brooks")
    println(pairTo.smaller)

    val pairTwo=new PairTwo("wer","ert")
    pairTwo.replaceFirst("sdgsd")

  }
}

class PairTwo[T](val first: T, val second: T) {
  def replaceFirst(newFirst: T) = new PairTwo[T](newFirst, second)
}

class Pair[T, S](val first: T, val second: S) {
  def getMiddle[H](a: Array[H]) {
    for (i <- a) {
      println(i)
    }
  }

}

class PairTo[T <: Comparable[T]](val first: T, val second: T) {
  def smaller = if (first.compareTo(second) < 0) first else second
}
