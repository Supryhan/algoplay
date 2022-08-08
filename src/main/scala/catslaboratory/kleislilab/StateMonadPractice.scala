package catslaboratory.kleislilab

import cats.data.{IndexedStateT, State, StateT}
import cats.effect.IO
import catslaboratory.kleislilab.ReaderMonadPractice.{Bread, Cat, Name}
import cats.effect.unsafe.implicits.global

object StateMonadPractice extends App {
  case class CatsState(value: Boolean)

  val catState: StateT[IO, CatsState, Cat] = StateT[IO, CatsState, Cat] {
    state: CatsState =>
      IO.pure((state, Cat(Bread("init"), "name1")))
  }
  val nextTrueState: StateT[IO, CatsState, Cat] = StateT[IO, CatsState, Cat] {
    state: CatsState =>
      IO.pure((CatsState(true), Cat(Bread("final"), "name2")))
  }
  val tupl: (CatsState, Cat) = catState.run(CatsState(false)).unsafeRunSync()
  println(s"Run Tuple: $tupl")
  val `value`: Cat = catState.runA(CatsState(false)).unsafeRunSync()
  println(s"Run A: ${`value`}")
  val getStateAndValue: (CatsState, CatsState) = StateT.get[IO, CatsState].run(CatsState(true)).unsafeRunSync()
  println(s"Return the input state as state and as value without modifying it: ${getStateAndValue}")
  val setStateAndValue: (CatsState, Unit) = StateT.set[IO, CatsState](CatsState(true)).run(CatsState(true)).unsafeRunSync()
  println(s"Return the input state as state and as value - unit without modifying it all: ${setStateAndValue}")
  val state: CatsState = catState.runS(CatsState(false)).unsafeRunSync()
  println(s"Run State: $state")
  val newState: CatsState = catState.modify(cat => cat.copy(value = false)).runS(CatsState(true)).unsafeRunSync()
  println(s"State modify function: $newState")
  val stateAndNewValue: (CatsState, Name) = catState.inspect(catsState => "Hello World value!").run(CatsState(false)).unsafeRunSync()
  println(s"State inspect, but value was affected: $stateAndNewValue ")

  val t1: IndexedStateT[IO, CatsState, CatsState, (Cat, Cat)] = for {
    oneCat <- catState
    twoCat <- nextTrueState
  } yield (oneCat, twoCat)

  val t2: IndexedStateT[IO, CatsState, CatsState, (Cat, Cat)] =
    catState
      .flatMap(oneCat =>
        nextTrueState.map(twoCat =>
          (oneCat, twoCat)
        )
      )

  val result: (CatsState, (Cat, Cat)) = t1.run(CatsState(false)).unsafeRunSync()

  println(s"State for-comprehension: $result")

}
