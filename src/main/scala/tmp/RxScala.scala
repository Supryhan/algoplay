package tmp

object RxScala extends App {
  def foldRightUsingFoldLeft[A, B](seq: Seq[A], z: B)(f: (A, B) => B): B = foldLeft(seq.reverse, z)((b, a) => f(a, b))

  def foldLeft[A, B](seq: Seq[A], z: B)(f: (B, A) => B): B =
    seq match {
      case Nil => z
      case x :: xs => foldLeft(xs, f(z, x))(f)
    }

  val list: Seq[Int] = List(1, 2, 3, 4, 5, 6, 7)
  val sum = foldRightUsingFoldLeft(list, "") (_ + _)
  println(sum)
  print(list.toString())
}
