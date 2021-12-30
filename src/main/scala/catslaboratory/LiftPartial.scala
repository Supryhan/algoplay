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

  import cats.implicits._
//  val o1: Option[Int] = none
//  val o2: Option[Int] = 2.some

  val res1 = { println("o11:"); none }.flatMap(_ => { val e = 1 / 0; println("o21"); 2.some })
  val res2 = { println("o12:"); none } *> { /*val e = 1 / 0;*/ println("o22"); 2.some }

  println(s"flatMap result: $res1")
  println(s"*> result: $res2")

}
