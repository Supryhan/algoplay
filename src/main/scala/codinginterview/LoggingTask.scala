package codinginterview

/**
 * Task: Logging System
 * Description:
 * Develop a logging component for a web application. This component is responsible
 * for collecting messages from various parts of the system and storing them in a
 * log file.
 *
 * Requirements:
 *   - Messages should be written to a file that rotates daily.
 *   - There should be functionality to dynamically change the logging level
 *     (e.g., DEBUG, INFO, WARN, ERROR).
 *
 * Tasks for You:
 *   - Identify and fix any errors in this code.
 *   - Determine potential performance or security issues.
 *   - Suggest ways to optimize or improve this component.
 *
 * Potential Issues to Consider:
 *   - Resource Leak: Files are opened but not always properly closed in all usage scenarios.
 *   - Thread Safety: Use of PrintWriter may be unsafe in a multi-threaded environment.
 *   - Date Formatting: Potential issues with time zones or date formats.
 *   - Resource Management: Check how errors are handled during file writing.
 */

import java.io.{FileWriter, PrintWriter}
import java.time.format.DateTimeFormatter
import java.time.LocalDate

object LoggingTask extends App {


  private object LogLevel extends Enumeration {
    val DEBUG, INFO, WARN, ERROR = Value
  }

  private class Logger {
    private var currentLogLevel: LogLevel.Value = LogLevel.INFO
    private var logFileWriter: PrintWriter = _

    def changeLogLevel(newLevel: LogLevel.Value): Unit = {
      currentLogLevel = newLevel
    }

    def log(message: String, level: LogLevel.Value): Unit = {
      if (level >= currentLogLevel) {
        if (logFileWriter == null) {
          val dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
          val logFileName = s"log_${dtFormatter.format(LocalDate.now())}.txt"
          logFileWriter = new PrintWriter(new FileWriter(logFileName, true))
        }
        logFileWriter.println(s"${LocalDate.now()} [$level]: $message")
        logFileWriter.flush()
      }
    }

    def close(): Unit = {
      if (logFileWriter != null) {
        logFileWriter.close()
      }
    }
  }

  // Usage example
  private val logger = new Logger()
  logger.changeLogLevel(LogLevel.DEBUG)
  logger.log("Starting application.", LogLevel.INFO)
  logger.log("An error occurred.", LogLevel.ERROR)
  logger.close()
}



