package zio

import java.io.IOException

import scalaz.zio.App
import scalaz.zio.console._

object MyZioApp extends App {

  def run(args: List[String]) =
    myAppLogic
      .either
      .map((s: Either[IOException, Unit]) =>
        s.fold(
          _ => 1,
          _ => 0))

  val myAppLogic =
    for {
      _ <- putStrLn("Hello! What is your name?")
      n <- getStrLn
      _ <- putStrLn(s"Hello, ${n}, good to meet you!")
    } yield ()
}