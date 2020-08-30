package shapeless

object TypeLevelProgrSucc extends App {

  import scala.reflect.runtime.universe._

  def show[T](value: T)(implicit tag: TypeTag[T]) =
    tag.toString().replace("shapeless.TypeLevelProgrSucc", "")

  trait Nat

  class Zero extends Nat

  class Succ[N <: Nat] extends Nat

  type One = Succ[Zero]
  type Two = Succ[One]
  type Three = Succ[Two]
  type Four = Succ[Three]
  type Five = Succ[Four]
  type Six = Succ[Five]
  type Seven = Succ[Six]
  type Nine = Succ[Seven]
  type Ten = Succ[Nine]

  trait <[A <: Nat, B <: Nat]

  object < {
    implicit def lt[B <: Nat]: Zero < Succ[B] = new <[Zero, Succ[B]] {}

    implicit def inductive[A <: Nat, B <: Nat](implicit _lt: A < B): Succ[A] < Succ[B] = new <[Succ[A], Succ[B]] {}

    def apply[A <: Nat, B <: Nat](implicit _lt: A < B): A < B = _lt
  }

  val comparer1: Zero < Ten = <[Zero, Ten]
  val comparer2: One < Ten = <[One, Ten]
  val comparer3: Two < Ten = <[Two, Ten]
  val comparer4: Four < Ten = <[Four, Ten]

  println(show(List(1, 2, 3, 4, 5, 6, 7)))
}
