package problems.simple

import problems.simple.Simulate.runMachine

private[simple] case class State[S, +A](run: S => (S, A)) {
  def map[B](f: A => B): State[S, B] = flatMap(a => State.unit(f(a)))
  def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] = flatMap(a => sb.map(b => f(a, b)))
  def flatMap[B](f: A => State[S, B]): State[S, B] = State( s => {
    val (s1, a) = run(s)
    f(a).run(s1)
  })
}

private[simple] object State {
  def unit[S, A](a: A): State[S, A] = State(s => (s, a))
  def get[S]: State[S, S] = State(s => (s, s))
  def set[S](s: S): State[S, Unit] = State(s => (s, ()))
  def sequence[S, B](xs: List[State[S, B]]): State[S, List[B]] =
    xs.foldRight(unit[S, List[B]](List.empty))((s1, sb) => s1.map2(sb)(_ :: _))

}

private[simple] sealed trait Input
private[simple] case object Coin extends Input
private[simple] case object Turn extends Input

private[simple] case class Machine(locked: Boolean, candies: Int, coins: Int)

private[simple] object Simulate {
  def runMachine(inputs: List[Input]): State[Machine, (Int, Int)] = State(
    machine => {
      val processed = inputs.foldLeft(machine) {
        case (m @ Machine(_, 0, _), _) => m
        case (m @ Machine(true, _, _), Turn) => m
        case (m @ Machine(false, _, _), Coin) => m
        case (m @ Machine(false, _, _), Turn) => m.copy(locked = true, candies = m.candies - 1)
        case (m @ Machine(true, _, _), Coin) => m.copy(locked = false, coins = m.coins + 1)
      }
      (processed, (processed.candies, processed.coins))
    }
  )
}

private[simple] object RunStateMachine extends App {
  println(runMachine(List(Coin)).run(Machine(locked = true, 1, 1)))
  println(runMachine(List(Turn)).run(Machine(locked = false, 1, 1)))
}