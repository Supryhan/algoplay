package structures

object ListCombinerUtil extends App {

  val list: List[Int] = List(1, 2, -3, 4, 5, 0, -6, 7, 8, 9, 2, -3, 4, 5, 6, 7, -8, 9, 0)

  def update(list: List[Int]): List[Int] = {
    val t = list
      .foldLeft((List[Int](), List[Int](), List[Int]())) { (t3m, i) =>
        i match {
          case i if i < 0 => (i :: t3m._1, t3m._2, t3m._3)
          case i if i > 0 => (t3m._1, t3m._2, i :: t3m._3)
          case i if i == 0 => (t3m._1, i :: t3m._2, t3m._3)
        }
      }
    t._1.sorted ::: t._2 ::: t._3.sorted
  }

  println(update(list))
}
