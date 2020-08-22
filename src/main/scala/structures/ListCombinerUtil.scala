package structures

import scala.collection.mutable

object ListCombinerUtil extends App {

  val list: List[Int] = List(1, 2, -3, 4, 5, 0, -6, 7, 8, 9, 2, -3, 4, 5, 6, 7, -8, 9, 0)

  def update(x: List[Int]): List[Int] = {
    var m: mutable.HashMap[Int, List[Int]] = mutable.HashMap(0 -> Nil, 1 -> Nil, 2 -> Nil)
    x.foldLeft(m) { (m, i) =>
      i match {
        case i if i < 0 => m(0) = i :: m(0); m
        case i if i > 0 => m(2) = i :: m(2); m
        case i if i == 0 => m(1) = i :: m(1); m
      }
    }
    m(1) ::: m(0) ::: m(2)
  }

  print(update(list))
}
