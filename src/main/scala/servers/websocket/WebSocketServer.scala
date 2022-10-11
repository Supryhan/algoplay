package servers.websocket

import akka.Done
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.Try

object WebSocketServer extends App {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "akka-system")
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext

  val interface = "localhost"
  val port = 8080

  import akka.http.scaladsl.server.Directives._

  val route: Route = get {
    pathEndOrSingleSlash {
      complete("Welcome to websocket server")
    }
  }
  val binding: Future[Http.ServerBinding] = Http().newServerAt(interface, port).bind(route)

  println(s"Server is now online at http://$interface:$port\nPress RETURN to stop...")

  import scala.io.StdIn

  StdIn.readLine()

  binding
    .flatMap((x: Http.ServerBinding) => x.unbind())
    .onComplete((x: Try[Done]) => actorSystem.terminate())
  println("Server is down...")
}
