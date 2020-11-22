package dependent_type

object L22ShapelessTypeClassMultNat extends App {
  import scala.language.higherKinds

  sealed trait Nat {
    type This >: this.type <: Nat
    type ++ = Succ[This]
    type Add[M <: Nat] <: Nat
    type Mult[M <: Nat] <: Nat
  }

  type Zero = Zero.type

  object Zero extends Nat{
    override type This = Zero
    override type Add[M <: Nat] = M
    override type Mult[M <: Nat] = Zero
  }

  final class Succ[N <: Nat] extends Nat {
    override type This = Succ[N]
    override type Add[M <: Nat] = Succ[N# Add [M]]
    override type Mult[M <: Nat] = Succ[N# Mult [M]]
  }

  type One = Zero# ++
  type Two = One# ++
  type Three = Two# ++
  type Four = Three# ++
  type Five = Four# ++
  type Six = Five# ++
  type Seven = Six# ++
  type Eight = Seven# ++
  type Nine = Eight# ++
  type Ten = Nine# ++

  type +[N <: Nat, M <: Nat] = N# Add [M]
  type *[N <: Nat, M <: Nat] = N# Mult [M]

//  implicitly[Two# Mult [Three] =:= Six]
//  implicitly[Two * Three =:= Six]
  implicitly[Zero * Zero =:= Zero]
  implicitly[One# Add [Six] =:= Seven]
}
