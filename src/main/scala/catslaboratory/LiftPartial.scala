package catslaboratory

object LiftPartial extends App {

  val intMatcher: PartialFunction[Int, String] = {
    case 1 => "one"
  }

  val liftedIntMatcher: Int => Option[String] = intMatcher.lift

  println(liftedIntMatcher(1))
  println(liftedIntMatcher(0))

  println(intMatcher(1))
  // println(intMatcher(0)) - Exception scala.MatchError
}
