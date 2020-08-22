package catslaboratory

object TreesOps {

  implicit class FunctorOps[F[_], A](source: F[A]) {
    def map[B](function: A => B)(implicit functor: FunctorApi[F]): F[B] = functor.map(source)(function)
  }

  implicit val treeFunctor: FunctorApi[Tree] =
    new FunctorApi[Tree] {
      def map[A, B](tree: Tree[A])(func: A => B): Tree[B] =
        tree match {
          case Branch(left, right) => Branch(map(left)(func), map(right)(func))
          case Leaf(value) => Leaf(func(value))
        }
    }
}

trait FunctorApi[F[_]] {
  def map[A, B](value: F[A])(f: A => B): F[B]
}

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object TreeFactory {
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

  def leaf[A](value: A): Tree[A] = Leaf(value)
}
