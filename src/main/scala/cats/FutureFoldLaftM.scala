import cats.implicits._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.control.NonFatal

/**
  * @author Andrew Ivanov
  */
object FutureFoldLeftM extends App {

  type Env = String

  type ErrMsg = String

  final case class Prod(id: String)

  final case class Subscr(id: String, prod: Prod)

  final case class Conn(id: String, subscr: Subscr)

  def prods(e: Env): Future[List[Prod]] =
    e match {
      case "local" => Future.successful(List(Prod("id1"), Prod("id2"), Prod("id3")))
      case _ => Future.failed(new Exception("wrong env"))
    }

  def subscrs(p: Prod): Future[List[Subscr]] =
    if (p.id == "id1") Future.successful(List(Subscr("sub1", p), Subscr("sub2", p)))
    else Future.failed(new Exception(s"Error for loading subscrs for prod $p"))

  def conn(s: Subscr): Future[Conn] = {
    if (s.id == "sub1") Future.successful(Conn("connid", s))
    else Future.failed(new Exception(s"Could not find conn for $s"))
  }

  def process[A](data: Future[List[A]], err: List[String], acc: List[A]): Future[(List[String], List[A])] =
    data.map((x: List[A]) => (err, acc ++ x)).recoverWith {
      case NonFatal(e) => (err.:+(e.getMessage), acc).pure[Future]
    }

  val program = for {
    x <- prods("local")
    y <- x.foldLeftM((List.empty[String], List.empty[Subscr])) {
      case ((err, data), v) => process(subscrs(v), err, data)
    }
    (errs, data) = y
    z <- data.foldLeftM((errs, List.empty[Conn])) {
      case ((err, data), v) => process(conn(v).map(_ :: Nil), err, data)
    }
  } yield z

  val (errs, succ) = Await.result(program, Duration.Inf)

  println("*" * 10)
  println("Errors")
  errs.foreach(println)
  println("*" * 10)
  println("*" * 10)
  println("Success")
  succ.foreach(println)
  println("*" * 10)
}
