package catslaboratory.monadslab

object Monads extends App {
  val aList: List[String] = List("a", "b", "c")
  val bList: List[Int] = List(1, 2, 3)

  def combineLists(aList: List[String], bList: List[Int]): List[(String, Int)] =
    for {
      a <- aList
      b <- bList
    } yield (a, b)

  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
    def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => pure(f(a)))
  }

  def combine[M[_], A, B](ma: M[A], mb: M[B])(implicit m: Monad[M]): M[(A, B)] =
    m.flatMap(ma)(a => m.flatMap(mb)(b => m.pure((a, b))))

  implicit val listMonad: Monad[List] = new Monad[List] {
    override def pure[A](a: A): List[A] = List(a)
    override def flatMap[A, B](ma: List[A])(f: A => List[B]): List[B] = ma.flatMap(f)
  }

  println(combineLists(aList, bList))
  println(combine(aList, bList))
}
