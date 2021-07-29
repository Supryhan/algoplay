package problems.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._
import eu.timepit.refined.string._
import eu.timepit.refined.W

object RefinedTraining extends App {
  val aPositiveInteger: Refined[Int, Positive] = 42
  val aNegativeInteger: Refined[Int, Negative] = -100

  println(aPositiveInteger)
  println(aNegativeInteger)

  val aNegative: Int Refined Negative = -100
  val nonNegative: Int Refined NonNegative = 1
  val anOdd: Int Refined Odd = 3
  val anEven: Int Refined Even = 68

  val d: W.`3.14`.T = 3.14
  println(d)

  val smallEnough: Int Refined Less[W.`100`.T] = 45
  println(smallEnough)

  val commandPrompt: String Refined EndsWith[W.`"$"`.T] = "daniel@mbp $"
  println(commandPrompt)

}
