package servers.rabbitmq

import com.rabbitmq.client.{Connection, ConnectionFactory, Channel}
object HelloRabbitMQ extends App {

  val QUEUE_NAME = "WSuser-queue"
  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()

  try {
    channel.queueDeclare(QUEUE_NAME, true, false, false, null)
    val message = "Hello World from scala"

    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    if (channel != null) channel.close()
    if (connection != null) connection.close()
  }

}
