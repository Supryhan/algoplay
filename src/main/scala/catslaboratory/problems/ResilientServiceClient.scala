package catslaboratory.problems

import cats.effect.{IO, ExitCode, IOApp}
import scala.concurrent.duration._
import scala.util.control.NonFatal

trait ServiceClient {
  def fetchData: IO[String]
}

class NonRetriableException extends Exception

class MockServiceClient extends ServiceClient {
  def fetchData: IO[String] = IO {
    "Some data" // Fake data
  }
}

class ResilientServiceClient(client: ServiceClient) {
  def safeFetchData: IO[String] = {
    val retryPolicy: IO[String] => IO[String] = retryWithBackoff(5, 2.seconds)
    retryPolicy(client.fetchData).handleErrorWith {
      case e: NonRetriableException => IO.raiseError(e)
      case NonFatal(e) => IO.pure("Failed to fetch data")
    }
  }

  private def retryWithBackoff(maxRetries: Int, delay: FiniteDuration): IO[String] => IO[String] = { action =>
    action.handleErrorWith { error =>
      if (maxRetries > 0)
        IO.sleep(delay) *> retryWithBackoff(maxRetries - 1, delay * 2)(action)
      else
        IO.raiseError(error)
    }
  }
}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val client = new ResilientServiceClient(new MockServiceClient)
    val fetchProcess = for {
      _ <- IO(println("Starting data fetch..."))
      data <- client.safeFetchData.timeout(10.seconds)
      _ <- IO(println(s"Received data: $data"))
    } yield ()

    for {
      i <- IO.pure(42).handleErrorWith {error =>
      IO.pure (43)
    }
      _ <- IO(println(i))
    } yield ()
    fetchProcess.as(ExitCode.Success)
  }
}
