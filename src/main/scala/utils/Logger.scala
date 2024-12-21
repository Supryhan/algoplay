package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.locks.ReentrantLock

object Logger {
  private val lock = new ReentrantLock()
  private val datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def log(message: String): Unit = {
    lock.lock()
    try {
      val timestamp = LocalDateTime.now().format(datetimeFormatter)
      println(s"[$timestamp] - $message")
    } finally {
      lock.unlock()
    }
  }

}
