package akka

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object FnF extends App {
  val system = ActorSystem(Printer(), "Fire-and-Forget")

  val printer: ActorRef[Printer.PrintIt] = system

  printer ! Printer.PrintIt("message")
}

object Printer {
  case class PrintIt(msg: String) extends AnyVal

  def apply(): Behavior[PrintIt] =
    Behaviors.receive {
      case (context, PrintIt(msg)) =>
        context.log.info(msg)
        Behaviors.same
    }
}
