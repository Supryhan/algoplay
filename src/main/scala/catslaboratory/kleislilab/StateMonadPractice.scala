package catslaboratory.kleislilab

import cats.data.StateT
import cats.effect.IO
import catslaboratory.kleislilab.ReaderMonadPractice.{Bread, Cat}
import cats.effect.unsafe.implicits.global

object StateMonadPractice extends App {
  case class CatsState(value: Boolean)

  val catState: StateT[IO, CatsState, Cat] = StateT[IO, CatsState, Cat] {
    state: CatsState =>
      IO.pure((CatsState(true), Cat(Bread(""), "name")))
  }
  val nextState1: StateT[IO, CatsState, Cat] = catState.map[Cat](f => Cat(Bread("2"), "name2"))


}
