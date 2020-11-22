package dependent_type

object L21ShapelessBoolOr extends App {

  import scala.language.higherKinds

  sealed trait Bool {
    type Or[B <: Bool] <: Bool
  }

  object True extends Bool {
    override type Or[B <: Bool] = True
  }

  object False extends Bool {
    override type Or[B <: Bool] = B
  }

  type ||[B1 <: Bool, B2 <: Bool] = B1# Or [B2]

  type True = True.type
  type False = False.type

  implicitly[True# Or [False] =:= True]
  implicitly[True || False =:= True]
}
