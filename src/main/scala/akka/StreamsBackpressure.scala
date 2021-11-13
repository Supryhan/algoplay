package akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.{Flow, Sink, Source}

object StreamsBackpressure extends App {

  implicit val system = ActorSystem(Behaviors.empty, "Streams-Backpressure")

  val source = Source(0 to 99)
  val flow = Flow[Int].map { i =>
    Thread.sleep(100)
    i + 1
  }
  val sink = Sink.foreach[Int](r => println(s">>>${r.toString}"))

  val result = source.via(flow).to(sink)
  result.run()

  system.terminate()
}
