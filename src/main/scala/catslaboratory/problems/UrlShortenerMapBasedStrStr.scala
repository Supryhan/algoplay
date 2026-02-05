package catslaboratory.problems

import cats.effect.{IO, IOApp, Ref}

object UrlShortenerMapBasedStrStr extends IOApp.Simple {

  def createShortUrl(url: String, storage: Ref[IO, Map[String, String]]): IO[String] = {
    for {
      shortCode <- IO(scala.util.Random.alphanumeric.take(6).mkString(""))
      _ <- storage.update(currentMap => currentMap + (shortCode -> url))
    } yield shortCode
  }

  def resolveShortUrl(shortCode: String, storage: Ref[IO, Map[String, String]]): IO[Option[String]] = {
    storage.get.map(_.get(shortCode))
  }

  val startStorage: IO[Ref[IO, Map[String, String]]] = Ref.of[IO, Map[String, String]](Map.empty)

  val run: IO[Unit] = for {
    storage <- startStorage
    shortCode <- createShortUrl("https://example.com", storage)
    _ <- IO.println(s"Shortened URL to: $shortCode")
    originalUrl <- resolveShortUrl(shortCode, storage)
    _ <- originalUrl match {
      case Some(url) => IO.println(s"Original URL was: $url")
      case None => IO.println("URL not found.")
    }
  } yield ()

}
