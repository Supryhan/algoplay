package catslaboratory

object CatsTraining extends App {

  trait Sem[A] {
  }

  trait Mon[A] extends Sem[A] {
    def em: A

    def \|+|/(a1: A, a2: A): A

    def comb[A](a1: A, a2: A)(implicit m: Mon[A]): A = m.\|+|/(a1, a2)
  }

  object Monoid {
    implicit val str: Mon[String] = new Mon[String] {
      def \|+|/(a1: String, a2: String): String = s"$a1$em$a2"

      def em: String = "->"
    }

    implicit val int: Mon[Int] = new Mon[Int] {
      def \|+|/(a1: Int, a2: Int): Int = s"$a1$em$a2".toInt

      def em: Int = 0
    }
  }

  implicit class C[A](val a: A) extends AnyVal {
    def |+|(aa: A)(implicit monoid: Mon[A]): A = monoid.comb(a, aa)
  }

  import Monoid._

  println("a1" |+| "b4")
  println(1 |+| 4)

}