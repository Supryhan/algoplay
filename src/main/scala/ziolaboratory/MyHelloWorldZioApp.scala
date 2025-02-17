package ziolaboratory

import zio.Console.{printLine, readLine}
import zio.{ZIO, ZIOAppDefault}

import java.io.IOException

object MyHelloWorldZioApp extends ZIOAppDefault {

  def run: ZIO[Any, Nothing, Int] =
    myAppLogic
      .either
      .map { (s: Either[IOException, Unit]) =>
        s.fold(
          _ => 1,
          _ => 0)
      }

  private val myAppLogic: ZIO[Any, IOException, Unit] =
    for {
      _ <- printLine("Hello! What is your name?")
      n <- readLine
      _ <- printLine(s"Hello, $n, good to meet you!")
    } yield ()
}