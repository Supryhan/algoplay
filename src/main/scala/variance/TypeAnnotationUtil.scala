package variance

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object TypeAnnotationUtil extends App {

  printEntity(Future.successful("string"))
  printEntity(Future.successful(List(1, 2, 3, 4, 5)))

  def printEntity[T](entity: Future[T]) = {
    entity onComplete {
      case Success(value) => value match {
        case listS: List[_] => listS.foreach(item => println(s">>>$item"))
        case string: String => println(s">>>$string")
      }
      case Failure(exception) => println(s">>>$exception")
    }
    import scala.language.postfixOps
    Await.result(entity, 3 second)
  }
}
