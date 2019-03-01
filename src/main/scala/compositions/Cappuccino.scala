package compositions

import scala.util.{Failure, Success}

/**
  * Created by dell on 15.03.2017.
  */
object Cappuccino extends App {
  val cappuccino = new Cappuccino
  cappuccino.prepareCappuccino() match {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  cappuccino.prepareCappucinoFlatMap() match {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
}
class Cappuccino {

  import scala.util.Try

  // Определим осмысленные синонимы:
  type CoffeeBeans = String
  type GroundCoffee = String

  case class Water(temperature: Int)

  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  // Методы-заглушки для отдельных шагов алгоритма:
  def grind(beans: CoffeeBeans): GroundCoffee = s"ground coffee of $beans"

  def heatWater(water: Water): Water = water.copy(temperature = 85)

  def frothMilk(milk: Milk): FrothedMilk = s"frothed $milk"

  def brew(coffee: GroundCoffee, heatedWater: Water): Espresso = "espresso"

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  // Исключения, на случай если что-то пойдёт не так
  // (они понадобяться нам позже):
  case class GrindingException(msg: String) extends Exception(msg)

  case class FrothingException(msg: String) extends Exception(msg)

  case class WaterBoilingException(msg: String) extends Exception(msg)

  case class BrewingException(msg: String) extends Exception(msg)

  // последовательно выполним алгоритм:
  def prepareCappuccino(): Try[Cappuccino] = for {
    ground <- Try(grind("arabica beans"))
    water <- Try(heatWater(Water(25)))
    espresso <- Try(brew(ground, water))
    foam <- Try(frothMilk("milk"))
  } yield combine(espresso, foam)

  def prepareCappucinoFlatMap(): Try[Cappuccino] =
    Try(grind("arabica beans")).flatMap(ground =>
      Try(heatWater(Water(25))).flatMap(water =>
        Try(brew(ground, water)).flatMap(espresso =>
          Try(frothMilk("milk")).map(foam =>
            combine(espresso, foam)
          )
        )
      )
    )
}
