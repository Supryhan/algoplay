package dependent_type

object L21ShapelessBoolIsEqual extends App {

  import scala.language.higherKinds

  sealed trait Bool {
    type Not <: Bool
    type IsEqual[B <: Bool] <: Bool
  }

  object True extends Bool {
    override type Not = False
    override type IsEqual[B <: Bool] = True#And[B]
    type And[B <: Bool] = B
    type Or[B <: Bool] = True
  }

  object False extends Bool {
    override type Not = True
    override type IsEqual[B <: Bool] = False#Or[B]#Not
    type And[B <: Bool] = False
    type Or[B <: Bool] = B
  }

  type True = True.type
  type False = False.type

  type ![B <: Bool] = B#Not
  type ==[B1 <: Bool, B2 <: Bool] = B1#IsEqual[B2]

  implicitly[True#IsEqual[False] =:= False]
  implicitly[True#IsEqual[True] =:= True]
  implicitly[True == False =:= False]

  implicitly[True#Not =:= False]
  implicitly[![True] =:= False]
}