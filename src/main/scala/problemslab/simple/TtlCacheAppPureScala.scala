package problemslab.simple

import scala.collection.mutable
import scala.concurrent.duration._
import java.util.concurrent.{Executors, TimeUnit}

class SimpleCache[A](ttl: FiniteDuration) {
  private val cache = mutable.Map[String, (A, Long)]()
  private val scheduler = Executors.newSingleThreadScheduledExecutor()

  def set(key: String, value: A): Unit = synchronized {
    cache(key) = (value, System.currentTimeMillis() + ttl.toMillis)
    schedulePurge()
  }

  def get(key: String): Option[A] = synchronized {
    cache.get(key).flatMap { case (value, expiryTime) =>
      if (System.currentTimeMillis() > expiryTime) {
        cache -= key
        None
      } else {
        Some(value)
      }
    }
  }

  private def schedulePurge(): Unit = {
    scheduler.schedule(new Runnable {
      def run(): Unit = purgeExpired()
    }, ttl.toMillis, TimeUnit.MILLISECONDS)
  }

  private def purgeExpired(): Unit = synchronized {
    val currentTime = System.currentTimeMillis()
    cache.filterInPlace { case (_, (_, expiryTime)) => expiryTime > currentTime }
  }

  def stop(): Unit = {
    scheduler.shutdown()
    scheduler.awaitTermination(5, TimeUnit.SECONDS)
  }
}

object TtlCacheAppPureScala extends App {
  val cache = new SimpleCache[Int](10.seconds)

  cache.set("test", 124)
  println(s"Initial get: ${cache.get("test")}") // Some(123)

  Thread.sleep(5000)
  println(s"Get after 5 seconds: ${cache.get("test")}") // Some(123)

  Thread.sleep(6000)
  println(s"Get after 11 seconds: ${cache.get("test")}") // None

  cache.stop()
}
