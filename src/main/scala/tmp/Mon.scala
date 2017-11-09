package tmp

object Mon extends App {
  print(FoldLeftContainer.sum(List(1, 2, 3)))
  print(FoldLeftContainer.sum(Seq(1, 2, 3)))

//  import MonoidOperations.toMonoidOp
//
//  print(1 |\+++/| (2))
//  print("1".|\+++/|("2"))
//  val i: Int = 13
//  i.times _

  //  implicit class Int_times(val numTimes: Int) extends AnyVal {
  //    def times(f: => _) {
  //      var i = numTimes
  //      while (i > 0) {
  //        f; i -= 1
  //      }
  //    }
  //  }
}

trait Monoid[A] {
  def mapper(a: A, b: A): A
  def zero: A
}

object Monoid {
  implicit val Int2Monoid: Monoid[Int] = new Monoid[Int] {
    def mapper(a: Int, b: Int): Int = a + b
    def zero: Int = 0
  }
  implicit val String2Monoid: Monoid[String] = new Monoid[String] {
    def mapper(a: String, b: String): String = a + b
    def zero: String = ""
  }
}

//trait MonoidOperations[C] {
//  val F: Monoid[C]
//  val value: C
//  def |\+++/|(a2: C) = F.mapper(value, a2)
//}

//object MonoidOperations {
//  implicit def toMonoidOp[A: Monoid](a: A): MonoidOperations[A] = new MonoidOperations[A] {
//    val F = implicitly[Monoid[A]]
//    val value = a
//  }
//}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit val FoldLeft2List: FoldLeft[List] = new FoldLeft[List] {
    def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  }
  implicit val FoldLeft2Seq: FoldLeft[Seq] = new FoldLeft[Seq] {
    def foldLeft[A, B](xs: Seq[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  }
}

object FoldLeftContainer {
//  def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
//  def foldLeft[A, B](xs: Seq[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  def sum[M[_], A](xs: M[A])(implicit m: Monoid[A], f: FoldLeft[M]): A = f.foldLeft(xs, m.zero, m.mapper)
}


//super
//ask pattern
//!?
//!!
//withFilter
