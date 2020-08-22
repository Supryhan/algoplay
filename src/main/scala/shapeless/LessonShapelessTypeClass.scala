package shapeless

object LessonShapelessTypeClass extends App {
  sealed trait Bool
  object True extends Bool
  type True = True.type
  object False extends Bool
  type False = False.type

  trait Or[B1 <: Bool, B2 <: Bool] {
    type Out <: Bool
  }

  object Or {
    type Aux[B1 <: Bool, B2 <: Bool, Out0 <: Bool] = Or[B1, B2] { type Out = Out0 }
    def instance[B1 <: Bool, B2 <: Bool, Out0 <: Bool]: Aux[B1, B2, Out0] = new Or[B1, B2] { type Out = Out0 }

    implicit def trueOr[B <: Bool]: Or.Aux[True, B, True] = instance
    implicit def falseOr[B <: Bool]: Or.Aux[False, B, B] = instance
  }

  implicitly[Or.Aux[True, False, True]]
}
