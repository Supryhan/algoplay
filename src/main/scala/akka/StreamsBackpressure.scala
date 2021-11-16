package akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, MINUTES}


object StreamsBackpressure extends App {

  implicit val system = ActorSystem(Behaviors.empty, "Streams-Backpressure")

  val source: Source[Int, NotUsed] = Source(0 to 99)
  val flow = Flow[Int].map { i =>
    println(s"$i")
    i + 1
  }
  val sink = Sink.foreach[Int]{ r =>
    Thread.sleep(100)
    println(s">>>${r.toString}")
  }

  val result = source.via(flow.buffer(16, OverflowStrategy.backpressure)).async.to(sink)
  result.run()

  Await.ready(system.whenTerminated, Duration(1, MINUTES))
}
