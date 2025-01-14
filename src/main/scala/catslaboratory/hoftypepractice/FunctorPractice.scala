package catslaboratory.hoftypepractice

import cats.Functor

import scala.util.Try

object FunctorPractice extends App {


  object Tree {
    // "smart" constructors
    def leaf[T](value: T): Tree[T] = Leaf(value)

    def branch[T](value: T, left: Tree[T], right: Tree[T]): Tree[T] = Branch(value, left, right)
  }

  sealed trait Tree[+T]

  case class Leaf[T](value: T) extends Tree[T]

  case class Branch[T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]

  implicit object TreeFunctor extends Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Leaf(v) => Leaf(f(v))
      case Branch(v, left, right) => Branch(f(v), map(left)(f), map(right)(f))
    }
  }

  import cats.syntax.functor._
  def do10x[F[_]](container: F[Int])(implicit functor: Functor[F]): F[Int] = functor.map(container)(_ * 10)
  def do10xBrief[F[_] : Functor](container: F[Int]): F[Int] = container.map(_ * 10)

  val tree: Tree[Int] = Tree.branch(40, Tree.branch(5, Tree.leaf(10), Tree.leaf(30)), Tree.leaf(20))
  val incrementedTree = tree.map(_ + 1)
  println(s"incrementedTree: $incrementedTree")

  // TODO 2: write a shorted do10x method using extension methods


  println(do10x(List(1, 2, 3)))
  println(do10x(Option(2)))
  println(do10x(Try(35)))
  println(s"[1]: ${do10x(     Tree.branch(30, Tree.leaf(10), Tree.leaf(20)))}")
  println(s"[2]: ${do10xBrief(Tree.branch(30, Tree.leaf(10), Tree.leaf(20)))}")


}


