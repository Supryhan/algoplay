package mycats

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.control.NonFatal
import cats.implicits._

/**
  * @author Andrew Ivanov
  */
object FutureFoldLeftM extends App {

  type Env = String

  type ErrMsg = String

  final case class Product(id: String)

  final case class Subscription(id: String, product: Product)

  final case class Connection(id: String, subscription: Subscription)

  def products(e: Env): Future[List[Product]] =
    e match {
      case "local" => Future.successful(List(Product("id1"), Product("id2"), Product("id3")))
      case _ => Future.failed(new Exception("wrong env"))
    }

  def subscriptions(p: Product): Future[List[Subscription]] =
    if (p.id == "id1") Future.successful(List(Subscription("sub1", p), Subscription("sub2", p)))
    else Future.failed(new Exception(s"Error for loading subscrs for prod $p"))

  def connection(s: Subscription): Future[Connection] = {
    if (s.id == "sub1") Future.successful(Connection("connid", s))
    else Future.failed(new Exception(s"Could not find conn for $s"))
  }

  def process[A](data: Future[List[A]], err: List[String], acc: List[A]): Future[(List[String], List[A])] =
    data.map((x: List[A]) => (err, acc ++ x)).recoverWith {
      case NonFatal(e) => (err.:+(e.getMessage), acc).pure[Future]
    }

  val program = for {
    x <- products("local")
    y <- x.foldLeftM((List.empty[String], List.empty[Subscription])) {
      case ((err, data1), v: Product) => process(subscriptions(v), err, data1)
    }
    (errs, data) = y
    z <- data.foldLeftM((errs, List.empty[Connection])) {
      case ((err, data2: List[Connection]), v: Subscription) => process(connection(v).map(_ :: Nil), err, data2)
    }
  } yield z

  val (errs, succ) = Await.result(program, Duration.Inf)

  println("*" * 10)
  println("Errors:")
  errs.foreach(println)
  println("*" * 10)
  println("Success:")
  succ.foreach(println)
  println("*" * 10)
}
