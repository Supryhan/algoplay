package implicits

object MonoidSyntax extends App {

  trait Monoid[A] {
    def empty: A

    def append(a1: A, a2: A): A

    def concat(list: List[A]): A = list.foldRight(empty)(append)
  }

  val intMonoid = new Monoid[Int] {
    def empty: Int = 0

    def append(a1: Int, a2: Int): Int = a1 + a2
  }
  val boolMonoid = new Monoid[Boolean] {
    def empty: Boolean = false

    def append(a1: Boolean, a2: Boolean): Boolean = a1 && a2
  }

  trait Show[A] {
    def show(a: A): String
  }

  val intShow = new Show[Int] {
    def show(a: Int): String = "+++"
  }

  def combineValues[T](map: Map[String, List[T]])(monoid: Monoid[T]): Map[String, T] = map.mapValues(monoid.concat)

  implicit class MonoidSyntax[A](val a: A) extends AnyVal {
    def |+|(a2: A)(implicit monoid: Monoid[A]): A = monoid.append(a, a2)
  }

  implicit class ShowSyntax[A](val a: A) extends AnyVal {
    def show(implicit show: Show[A]): String = show.show(a)
  }

  def addAndShow[A: Monoid : Show](a1: A, a2: A): String = (a1 |+| a2).show

  print(addAndShow(true, false))
  print(addAndShow(1, 2))
}
