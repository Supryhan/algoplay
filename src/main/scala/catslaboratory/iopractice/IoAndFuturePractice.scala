package catslaboratory.iopractice

import scala.concurrent.{ExecutionContext, Future}
import cats.effect.IO
import cats.effect.unsafe.IORuntime

import scala.concurrent.duration.DurationInt

object IoAndFuturePractice extends App {

  implicit val runtime: IORuntime = IORuntime.global
  implicit val ec: ExecutionContext = runtime.compute

  eagerOiFromFutureExample()
  nonEagerOiFromFutureExample()

  def eagerOiFromFutureExample() {
    println(">>>>>>>>>>>>>>>>>>>eagerOiFromFutureExample<<<<<<<<<<<<<<<<<<<<<<<")
    println("Start PureFutureExample")


    val future = Future(println("This Future executes immediately"))
    for {
      _ <- IO.sleep(1.millis)
    } yield ()
    val io = IO.pure(future)

    println("Before unsafeRunSync")
    io.unsafeRunSync()
    println("After unsafeRunSync")
  }


  def nonEagerOiFromFutureExample(): Unit = {
    println(">>>>>>>>>>>>>>>>>>nonEagerOiFromFutureExample<<<<<<<<<<<<<<<<<<<<<")
    println("Start DelayedFutureExample")

    val io = IO.fromFuture(IO {
      println("Creating Future inside IO")
      val future = Future(println("This Future executes when IO is run"))
      for {
        _ <- IO.sleep(1.millis)
      } yield ()
      future
    })


    println("Before unsafeRunSync")
    io.unsafeRunSync() // Launch Future inside IO
    println("After unsafeRunSync")
  }
}
