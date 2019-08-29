package mycats

import cats._
import cats.implicits._

object ContravariantFunctorShow extends App {


  implicit val showMoney: Show[Money] = Show.show(m => s"$$${m.amount}")

                                                 //           Show[M] =>  (S => M)  => Show[S]
                                                 // contramap(value: F[A])(f: B => A): F[B]
  implicit val showSalary: Show[Salary] = showMoney.contramap[Salary](salary => salary.size)
  Salary(Money(1000)).show
}

case class Money(amount: Int)

case class Salary(size: Money)