package catslaboratory

object CatsTraining extends App {

  implicit class C[A](val a: A) extends AnyVal {
    def combineWith(aa: A)(implicit monoid: Monoid[A]): A = monoid.combine(a, aa)
  }

  println("a1" combineWith "b4")
  println(1 combineWith 4)
}

trait Semigroup[A] {
  def combine(a1: A, a2: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  implicit val str: Monoid[String] = new Monoid[String] {
    def empty: String = "->"

    def combine(a1: String, a2: String): String = s"$a1$empty$a2"
  }

  implicit val int: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0

    def combine(a1: Int, a2: Int): Int = s"$a1$empty$a2".toInt
  }
}