package websocket

import akka.actor.{Actor, ActorSystem}

import scala.io.StdIn

object WebSocketServer extends App {

  implicit val actorSystem = ActorSystem("akka-system")
//  implicit val flowMaterializer = ActorMaterializer()
//
//  val interface = "localhost"
//  val port = 8080
//
//  import Directives._
//
//  val route = get {
//    pathEndOrSingleSlash {
//      complete("Welcome to websocket server")
//    }
//  }
//  val binding = Http().bindAndHandle(route, interface, port)
//  println(s"Server is now online at http://$interface:$port\nPress RETURN to stop...")
//  StdIn.readLine()
//
//  import actorSystem.dispatcher
//
//  binding.flatMap(_.unbind()).onComplete(_ => actorSystem.shutdown())
//  println("Server is down...")
}
