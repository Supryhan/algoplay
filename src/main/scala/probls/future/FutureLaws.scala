package probls.future

import java.math.BigInteger
import scala.concurrent.Future

object FutureLaws extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val fl1: Future[Unit] = Future { Thread.sleep(2000); println("f1 printed") }
  val fl2: Future[Unit] = Future { Thread.sleep(1000); println("f2 printed") }

  val el: Future[Unit] = for {
    _ <- fl1
    _ <- fl2
  } yield ()
  println(s"Vals: $el")

  def fd1: Future[Unit] = Future { Thread.sleep(2000); println("f1 printed") }
  def fd2: Future[Unit] = Future { Thread.sleep(1000); println("f2 printed") }

  val ed: Future[Unit] = for {
    _ <- fd1
    _ <- fd2
  } yield ()

  println(s"Defs: $ed")

}
