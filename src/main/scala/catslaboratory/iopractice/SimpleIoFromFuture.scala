package catslaboratory.iopractice


import cats.effect.IO
import cats.effect.unsafe.implicits.global

import scala.concurrent._
import scala.concurrent.duration._

object SimpleIoFromFuture extends App {

  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  val future = Future {
    println("I'm a Future")
  }

  val unitF = for {
    _ <- future
    _ <- future
  } yield ()

  trait Dff {
    def df()
    def ddfd() = {}
  }

  abstract class DParent(a2: Int, a1: Int, a: Int) {
    def this() = this(1, 1, 0) // Додатковий конструктор для спрощення ініціалізації

    def g(): Unit
  }

  class DChild(a2: Int, a1: Int, a: Int) extends DParent(a2,a1,a) {
    def this(a: Int) = this(1,1,a) // Викликає додатковий конструктор DParent

    override def g(): Unit = println("Implement me!")
  }


  val r: Int = Int.box(42)

  // Використання вторинного конструктора DChild
  val example = new DChild(5)

  val r1 = Await.result(unitF, 5.seconds)
  println(s"print r1 after await: $r1")

  val e1: IO[Unit] = IO.fromFuture(IO {
    Future(println("print e1 workflow"))
  })
  println(s"print e1 on stop the world: ${e1.unsafeRunSync}")

  val e2: IO[Int] = IO.fromOption(Some(42))(new IllegalArgumentException("This is just exception."))
  println(s"print e2 on stop the world: ${e2.unsafeRunSync}")

  val e3: IO[Int] = IO.fromOption(None)(new IllegalArgumentException("This is artificial exception."))
  // This line will cause exception and print stack trace because of exception in previous line
  e3.unsafeRunSync
  //  println(s"print e3 on stop the world: ${e3.unsafeRunSync}")

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 43
    case 8 => 56
    case 100 => 999
  }

  aPartialFunction.applyOrElse(2, (x: Int) => -1) // return -1

  val nums = List(1, 2, 3, 8, 100)
  val results = nums.collect(aPartialFunction) // List(43, 56, 999)

  def make[T <: Product] (value: T): String = {
    s"""
      |${value.productArity}
      |${value.productElementName(0)}: ${value.productElement(0)}
      |${value.productElementName(1)}: ${value.productElement(1)}
      |""".stripMargin
  }
  case class Ppp(e: Int, e2: String)

  val d = make(Ppp(42, "43"))
  println(s"D: $d")

}
