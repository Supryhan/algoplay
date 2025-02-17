package ziolaboratory.problemsolving

import zio._
import zio.Console.printLine

case class CacheEntry[A](value: A, expirationTime: Long)

final class Cache[A](ttl: Duration, cache: Ref[Map[String, CacheEntry[A]]]) {

  def get(key: String): UIO[Option[A]] =
    for {
      currentTime <- Clock.nanoTime
      entries     <- cache.get
      result      <- entries.get(key) match {
        case Some(CacheEntry(value, expTime)) if expTime > currentTime =>
          ZIO.succeed(Some(value))
        case _ =>
          cache.update(_ - key) *> ZIO.succeed(None)
      }
    } yield result

  def set(key: String, value: A): UIO[Unit] =
    for {
      currentTime    <- Clock.nanoTime
      expirationTime  = currentTime + ttl.toNanos
      _              <- cache.update(_ + (key -> CacheEntry(value, expirationTime)))
    } yield ()

  def purgeExpired: UIO[Unit] =
    for {
      currentTime <- Clock.nanoTime
      _           <- cache.update(_.filter { case (_, CacheEntry(_, expTime)) =>
        expTime > currentTime
      })
    } yield ()
}

object Cache {
  def make[A](ttl: Duration): UIO[Cache[A]] =
    Ref.make(Map.empty[String, CacheEntry[A]]).map(new Cache(ttl, _))
}

object TtlCacheAppZio extends ZIOAppDefault {
  def run: ZIO[Any, Nothing, Unit] = (for {
    ttl   <- {val e: ZIO[Any, Nothing, zio.Duration] = ZIO.succeed(30.seconds); e}
    cache <- Cache.make[Int](ttl)
    _     <- cache.set("key1", 123)
    _     <- ZIO.sleep(10.seconds) *> cache.get("key1").flatMap(v => printLine(s"Value after 10 seconds: $v"))
    _     <- ZIO.sleep(25.seconds) *> cache.get("key1").flatMap(v => printLine(s"Value after 35 seconds: $v"))
    _     <- ZIO.sleep(5.seconds) *> cache.purgeExpired *> cache.get("key1").flatMap(v => printLine(s"Value after purging: $v"))
  } yield ()).orDie
}
