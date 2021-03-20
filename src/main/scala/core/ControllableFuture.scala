package core

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}

object ControllableFuture extends App {

  def getData(arg: Int): Future[String] = {
    val promise = Promise[String]()
    RemoteService.submitTask(arg) { x =>
      val remoteResult = RemoteService.produceValue(x)
      promise.success(remoteResult)
    }
    promise.future
  }

  val f = getData(42).map(_.toUpperCase)

  f.onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println("exception")
  }
  Await.result(f, Duration.Inf)
}

object RemoteService {
  def produceValue(arg: Int): String = s"Producer response: $arg"

  def submitTask[A](value: A)(function: A => Unit): Boolean = {
    function(value) //some inner, hidden & immutable logic
    true
  }
}