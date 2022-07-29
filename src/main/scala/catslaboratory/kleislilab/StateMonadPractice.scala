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
  println(s"Run Tuple: ${catState.run(CatsState(false)).unsafeRunSync()}")
  println(s"Run A: ${catState.runA(CatsState(false)).unsafeRunSync()}")
  println(s"Run State: ${catState.runS(CatsState(false)).unsafeRunSync()}")
  println(s"State modify: ${catState.modify(cat => cat.copy(value = false)).runS(CatsState(false)).unsafeRunSync()}")
  println(s"State inspect: ${catState.inspect(cat => "Hello World value!").run(CatsState(false)).unsafeRunSync()}")

  val t: IndexedStateT[IO, CatsState, CatsState, (Cat, Cat)] = for {
    oneCat <- catState
    twoCat <- nextTrueState
  } yield (oneCat, twoCat)

  val result: (CatsState, (Cat, Cat)) = t.run(CatsState(false)).unsafeRunSync()

  println(s"State for-comprehension: $result")

}
