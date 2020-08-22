package compositions

import scala.util.{Failure, Success}

object CappuccinoBase extends App {
  val cappuccinoBase = new CappuccinoBase
  cappuccinoBase.prepareCappuccino() match {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  cappuccinoBase.prepareCappucinoFlatMap() match {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
}

class CappuccinoBase {

  import scala.util.Try

  type CoffeeBeans = String
  type GroundCoffee = String

  case class Water(temperature: Int)

  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  def grind(beans: CoffeeBeans): GroundCoffee = s"ground coffee of $beans"

  def heatWater(water: Water): Water = water.copy(temperature = 85)

  def frothMilk(milk: Milk): FrothedMilk = s"frothed $milk"

  def brew(coffee: GroundCoffee, heatedWater: Water): Espresso = "espresso"

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  def prepareCappuccino(): Try[Cappuccino] = for {
    groundCoffee  <- Try(grind("arabica beans"))
    hotWater      <- Try(heatWater(Water(25)))
    espresso      <- Try(brew(groundCoffee, hotWater))
    foam          <- Try(frothMilk("milk"))
    combined      <- Try(combine(espresso, foam))
  } yield combined

  def prepareCappucinoFlatMap(): Try[Cappuccino] =
    Try(grind("arabica beans"))
      .flatMap(groundCoffee => Try(heatWater(Water(25)))
        .flatMap(hotWater    => Try(brew(groundCoffee, hotWater))
          .flatMap(espresso   => Try(frothMilk("milk"))
            .map(foam          => combine(espresso, foam)))))
}
