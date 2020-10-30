package catslaboratory

object CatsTraining extends App {

  implicit class C[A](val a: A) extends AnyVal {
    def combineWith(aa: A)(implicit monoid: MonoidCT[A]): A = monoid.combine(a, aa)
  }

  import MonoidCT._

  println("a1" combineWith "b4")
  println(1 combineWith 4)
}

trait SemigroupCT[A] {
  def combine(a1: A, a2: A): A
}

trait MonoidCT[A] extends SemigroupCT[A] {
  def empty: A
}

object MonoidCT {
  implicit val str: MonoidCT[String] = new MonoidCT[String] {
    def empty: String = "->"

    def combine(a1: String, a2: String): String = s"$a1$empty$a2"
  }

  implicit val int: MonoidCT[Int] = new MonoidCT[Int] {
    def empty: Int = 0

    def combine(a1: Int, a2: Int): Int = s"$a1$empty$a2".toInt
  }
}