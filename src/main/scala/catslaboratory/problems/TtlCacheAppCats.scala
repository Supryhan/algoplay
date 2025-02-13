package catslaboratory.problems

import cats.effect.{IO, IOApp, Ref}
import cats.effect.kernel.Temporal
import scala.concurrent.duration._

case class CacheEntry[A](value: A, expirationTime: Long)

class Cache[A](ttl: FiniteDuration)(implicit T: Temporal[IO]) {
  private val cache: Ref[IO, Map[String, CacheEntry[A]]] = Ref.unsafe(Map.empty)

  def get(key: String): IO[Option[A]] = {
    for {
      currentTime <- T.monotonic
      entries <- cache.get
      entry = entries.get(key)
      result <- entry match {
        case Some(CacheEntry(value, expTime)) if expTime > currentTime.toNanos =>
          IO.pure(Some(value))
        case _ =>
          cache.update(_ - key) *> IO.pure(None)
      }
    } yield result
  }

  def set(key: String, value: A): IO[Unit] = {
    for {
      currentTime <- T.monotonic
      expirationTime = currentTime.toNanos + ttl.toNanos
      _ <- cache.update(_ + (key -> CacheEntry(value, expirationTime)))
    } yield ()
  }

  def purgeExpired: IO[Unit] = {
    for {
      currentTime <- T.monotonic
      _ <- cache.update(_.filter {
        case (_, CacheEntry(_, expTime)) => expTime > currentTime.toNanos
      })
    } yield ()
  }
}

object TtlCacheAppCats extends IOApp.Simple {
  def run: IO[Unit] = {
    val ttl = 30.seconds
    val cache = new Cache[Int](ttl)
    for {
      _ <- cache.set("key1", 123)
      _ <- IO.sleep(10.seconds) *> cache.get("key1").flatMap(v => IO.println(s"Value after 10 seconds: $v"))
      _ <- IO.sleep(25.seconds) *> cache.get("key1").flatMap(v => IO.println(s"Value after 35 seconds: $v"))
      _ <- IO.sleep(5.seconds) *> cache.purgeExpired *> cache.get("key1").flatMap(v => IO.println(s"Value after purging: $v"))
    } yield ()
  }
}
