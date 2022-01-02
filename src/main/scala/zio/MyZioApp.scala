package zio

import zio.Console.{printLine, readLine}

import java.io.IOException

object MyZioApp extends ZIOAppDefault {

   def run: ZIO[Console, Nothing, Int] =
    myAppLogic
      .either
      .map((s: Either[IOException, Unit]) =>
        s.fold(
          _ => 1,
          _ => 0))

  val myAppLogic =
    for {
      _ <- printLine("Hello! What is your name?")
      n <- readLine
      _ <- printLine(s"Hello, ${n}, good to meet you!")
    } yield ()

}