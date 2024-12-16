package catslaboratory

import cats._
import cats.implicits._

object ContravariantFunctorShow extends App {

  case class Money(amount: String)
  case class Salary(size: Money)


  implicit val showMoney: Show[Money] = Show.show((m: Money) => s"money: $$${m.amount}")

                                                 //           Show[M] =>  (S => M)  => Show[S]
                                                 // contramap(value: F[A])(f: B => A): F[B]
  implicit val showSalary: Show[Salary] = showMoney.contramap[Salary](salary => salary.size)

  println("500".show)
  println(Money("100").show)
  println(Salary(Money("1000500")).show)
}