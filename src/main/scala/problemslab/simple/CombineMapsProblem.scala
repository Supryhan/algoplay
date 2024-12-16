package problemslab.simple

object CombineMapsProblem extends App {
  trait Monoid[A] {
    def empty: A
    def combine(a1: A, a2: A): A
    def concat(list: List[A]): A = list.foldLeft(empty)(combine)
  }

  object Monoid {
    implicit def intMonoidInstance: Monoid[Int] = new Monoid[Int] {
      def empty: Int = 0
      def combine(a1: Int, a2:Int): Int = a1 + a2
    }
    implicit def strMonoidInstance: Monoid[String] = new Monoid[String] {
      def empty: String = ""
      def combine(a1: String, a2:String): String = a1 + a2
    }
    implicit def listMonoidInstance[A]: Monoid[List[A]] = new Monoid[List[A]] {
      def empty: List[A] = List.empty[A]
      def combine(a1: List[A], a2:List[A]): List[A] = a1 ++ a2
    }
    implicit def optionMonoidInstance[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
      def empty: Option[A] = None
      def combine(a1: Option[A], a2:Option[A]): Option[A] = a1 orElse a2
    }

  }


  implicit class MonoidOps[A](a1: A) {
    def |+|(a2: A)(implicit instance: Monoid[A]): A = instance.combine(a1, a2)
  }
  private def combineMaps[A: Monoid](m: Map[String, List[A]]): Map[String, A] =
    m.view.mapValues(_.foldLeft(implicitly[Monoid[A]].empty)(_ |+| _)).toMap

  println(combineMaps(Map("a" -> List(List(1, 2), List(3)), "b" -> List(List("hello", "world"), List("!")))))
  //  Map(a -> List(1, 2, 3), b -> List(hello, world, !))
  println(combineMaps(Map("a" -> List(Option(3)), "b" -> List(Option("hello")), "c" -> List(None))))
  // Map(a -> Some(3), b -> Some(hello), c -> None)
}
