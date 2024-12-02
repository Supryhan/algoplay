package servers.rabbitmq

import java.util.concurrent.CountDownLatch

import com.rabbitmq.client.{AMQP, Channel, Connection, ConnectionFactory, DefaultConsumer, Envelope}

class RabbitMQService {

  val QUEUE_NAME = "WSuser-queue"
  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()

  def sendMessage(message: String): Unit = {
    if (channel != null) {
      try {
        channel.queueDeclare(QUEUE_NAME, true, false, false, null)
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"))
        println(" [x] Sent '" + message + "'")
      } catch {
        case e: Exception => e.printStackTrace()
      }
    }
  }

  def receiveMessages(latch: CountDownLatch): Unit = {
    val consumer = new DefaultConsumer(channel) {
      override def handleDelivery(consumerTag: String, envelope: Envelope, properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {
        val message = new String(body, "UTF-8")
        println(" [x] Received '" + message + "'")
        Thread.sleep(1000)
        channel.basicAck(envelope.getDeliveryTag(), false)
        latch.countDown()
      }
    }
    channel.basicConsume(QUEUE_NAME, false, consumer)
  }


  def close(): Unit = {
    if (channel != null) channel.close()
    if (connection != null) connection.close()
  }

}

object UseRabbitMQ extends App {
  val latch = new CountDownLatch(3)
  val rabbit = new RabbitMQService
  try {
    rabbit.sendMessage("Hello World from Scala #1")
    rabbit.sendMessage("Hello World from Scala #2")
    rabbit.sendMessage("Hello World from Scala #3")
    Thread.sleep(1000)
    rabbit.receiveMessages(latch)
    latch.await()
  } finally {
    rabbit.close()
  }
}