package dependent_type

object TypeLevelProgrSucc extends App {

  import scala.reflect.runtime.universe._

  def show[T](value: T)(implicit tag: TypeTag[T]) =
    tag.toString().replace("shapeless.TypeLevelProgrSucc.", "")

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
  type Eight = Succ[Seven]
  type Nine = Succ[Eight]
  type Ten = Succ[Nine]

  trait <[A <: Nat, B <: Nat]

  object < {
    implicit def lt[B <: Nat]: Zero < Succ[B] = new <[Zero, Succ[B]] {}

    implicit def inductive[A <: Nat, B <: Nat](implicit _lt: A < B): Succ[A] < Succ[B] = new <[Succ[A], Succ[B]] {}

    def apply[A <: Nat, B <: Nat](implicit _lt: A < B): A < B = _lt
  }

  val comparer1: Zero < Ten = <[Zero, Ten]
  val comparer3: Two < Ten = <[Two, Ten]
//  val comparer3err: Ten < Two = <[Ten, Two] // Compile error
  val comparer4: Four < Eight = <[Four, Eight]
  println(show(comparer1))

  trait <=[A <: Nat, B <: Nat]

  object <= {
    implicit def lt[B <: Nat]: Zero <= B = new <=[Zero, B] {}

    implicit def inductive[A <: Nat, B <: Nat](implicit _lte: A <= B): Succ[A] <= Succ[B] = new <=[Succ[A], Succ[B]] {}

    def apply[A <: Nat, B <: Nat](implicit _lte: A <= B): A <= B = _lte
  }

  def comparer5: Zero <= Zero = <=[Zero, Zero]
//  def comparer6err: One <= Zero = <=[One, Zero] // Compile error
  println(show(comparer5))
  implicitly[Seven <= Eight]
  implicitly[<=[Two, Ten]]


  sealed abstract class IsSameType[X, Y]
  object IsSameType {
    implicit def tpEquals[A]: IsSameType[A, A] = new IsSameType[A, A]{}
  }
  implicitly[IsSameType[String, String]]
//  implicitly[IsSameType[Int, String]] // Compile error

  println(show(List(1, 2, 3, 4, 5, 6, 7)))

  import scala.reflect.ClassTag

  object ListUtils {
    def retrieve[T](list: List[Any])(implicit tag: ClassTag[T]) =
      list.flatMap {
        case element: T => Some(element)
        case _ => None
      }
  }

  val list: List[Any] = List(3, 10, "string", List(), "anotherString")
  val result: List[String] = ListUtils.retrieve[String](list)
  println(s"result:$result") // List(string, anotherString)

}
