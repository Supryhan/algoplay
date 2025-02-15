package implicits

object MonoidSyntax extends App {

  trait Monoid[A] {
    def empty: A

    def append(a1: A, a2: A): A

    def concat(list: List[A]): A = list.foldRight(empty)(append)
  }

  object Monoid {
    implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
      def empty: Int = 0

      def append(a1: Int, a2: Int): Int = a1 + a2
    }
    implicit val BoolMonoid: Monoid[Boolean] = new Monoid[Boolean] {
      def empty: Boolean = false

      def append(a1: Boolean, a2: Boolean): Boolean = a1 && a2
    }
  }

  trait Show[A] {
    def convertToString(a: A): String
  }

  object Show {
    implicit val IntShow: Show[Int] = new Show[Int] {
      def convertToString(a: Int): String = a.toString
    }
    implicit val BoolShow: Show[Boolean] = new Show[Boolean] {
      def convertToString(a: Boolean): String = String.valueOf(a)
    }
  }

  def combineValues[T](map: Map[String, List[T]])(implicit monoid: Monoid[T]): Map[String, T] =
    map.view.mapValues(monoid.concat).toMap

  implicit class MonoidSyntax[A](val a1: A) extends AnyVal {
    def |+|(a2: A)(implicit monoid: Monoid[A]): A = monoid.append(a1, a2)
  }

  implicit class ShowSyntax[A](val a: A) extends AnyVal {
    def show(implicit show: Show[A]): String = show.convertToString(a)
  }

  def addAndShow[A: Monoid : Show](a1: A, a2: A): String = (a1 |+| a2).show

  println(addAndShow(true, false))
  println(addAndShow(1, 2))
}


