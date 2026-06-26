package com.supryhan.ziolaboratory

import zio.Console.{printLine, readLine}
import zio._

import java.io.IOException

object MyZioApp extends ZIOAppDefault {

  override def run: ZIO[Any, Nothing, Int] =
    myAppLogic
      .either
      .map { result: Either[IOException, Unit] =>
        result.fold(
          _ => 1,
          _ => 0
        )
      }

  val myAppLogic: ZIO[Any, IOException, Unit] =
    for {
      _ <- printLine("Hello! What is your name?")
      n <- readLine
      _ <- printLine(s"Hello, $n, good to meet you!")
    } yield ()
}