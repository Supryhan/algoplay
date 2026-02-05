package catslaboratory

import cats.effect.FiberIO

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}

object PrimitivesInCats extends App {

  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._

  type D[A] = A

  def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y

  import cats.instances.option._
  import cats.instances.list._

  println(sumSquare(Option(2), Option(5)))
  println(sumSquare(List(2), List(5, 6)))
  println(sumSquare(3: D[Int], 4: D[Int]))

  import cats.syntax.either._

  def countPositive(nums: List[Int]) =
    nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
      if (num > 0) {
        accumulator.map(_ + 1)
      } else {
        Left("Negative. Stopping!")
      }
    }

  println(countPositive(List(1, 2, 3))) // Right(3)
  println(countPositive(List(1, -2, 3))) // Left(Negative. Stopping!)

  println(Either.fromTry(scala.util.Try("foo".toInt)))
  //Left(java.lang.NumberFormatException: For input string: "foo")
  println(Either.fromOption[String, Int](None, "Badness"))
  //Left(Badness)

  (-1).asRight[String].ensure("Must be non-negative!")(_ > 0)
  "error".asLeft[Int].recover {
    case str: String => -1
  }
  "error".asLeft[Int].recoverWith {
    case str: String => Right(-1)
  }

  import cats.effect.{IO, Sync, Async, ExitCode}
  import java.io._
  import scala.concurrent.ExecutionContext
  import cats.effect.unsafe.implicits.global

  implicit val ec: ExecutionContext = ExecutionContext.global

  val e: IO[Int] = IO.pure(42)
  val k: IO[FiberIO[Int]] = e.start
  k.flatMap(i => IO.pure(i))
  e.unsafeRunSync()

  val task = IO.sleep(5.seconds) *> IO(println("Task completed"))

  val r: IO[ExitCode] = for {
    fiber <- task.start // Запуск задачі у вигляді Fiber
    _ <- IO.sleep(500.millis) // Зачекати півсекунди
    result <- fiber.join // Приєднатися до Fiber і отримати результат
  } yield {
    println(s"Result from Fiber: $result")
    ExitCode.Success
  }


  def writeToFile[F[_] : Sync](file: File, text: String): F[Unit] = Sync[F].delay {
    val pw = new PrintWriter(file)
    try {
      pw.write(text)
    } finally {
      pw.close()
    }
  }

  val action: IO[Unit] = writeToFile[IO](new File("test.txt"), "Hello, Sync!")
  action.unsafeRunSync()

  import java.util.concurrent.{Executors, ExecutorService}

//  def asyncComputation[F[_] : Async]: F[String] = Async[F].async { cb =>
//    // Припустимо, що це асинхронна мережева операція
//    ec.execute(() => {
//      try {
//        // Симуляція тривалої операції
//        Thread.sleep(1000)
//        cb(Right("Completed successfully"))
//      } catch {
//        case e: Exception => cb(Left(e))
//      }
//    })
//  }

//  // Використання asyncComputation в IO
//  val asyncAction: IO[String] = asyncComputation[IO]
//  asyncAction.unsafeRunAsync {
//    case Right(result) => println(result)
//    case Left(ex) => ex.printStackTrace()
//  }

}