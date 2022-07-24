package catslaboratory.kleislilab

import cats.data.{IndexedStateT, StateT}
import cats.effect.IO
import catslaboratory.kleislilab.ReaderMonadPractice.{Bread, Cat, Name}
import cats.effect.unsafe.implicits.global

object StateMonadPractice extends App {
  case class CatsState(value: Boolean)

  val catState: StateT[IO, CatsState, Cat] = StateT[IO, CatsState, Cat] {
    state: CatsState =>
      IO.pure((CatsState(true), Cat(Bread("init"), "name1")))
  }
  val nextTrueState: StateT[IO, CatsState, Cat] = StateT[IO, CatsState, Cat] {
    state: CatsState =>
      IO.pure((CatsState(true), Cat(Bread("final"), "name2")))
  }

  val result: IndexedStateT[IO, CatsState, CatsState, (Cat, Cat)] = for {
    a <- catState
    b <- nextTrueState
  } yield (a, b)

  println(result.run(CatsState(false)).unsafeRunSync())

}
