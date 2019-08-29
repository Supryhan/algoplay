package mycats

import cats._
import cats.implicits._

object ContravariantFunctorShow extends App {


  implicit val showMoney: Show[Money] = Show.show(m => s"$$${m.amount}")
  implicit val showSalary: Show[Salary] = showMoney.contramap(_.size)

  Salary(Money(1000)).show
}

case class Money(amount: Int)

case class Salary(size: Money)