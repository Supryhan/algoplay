package problems

import scala.collection.JavaConverters._
import scala.concurrent.Future

object InterviewTask {
  /**
    * Объединят элементы последовательности в группы так, чтобы для любых
    * рядом стоящих элементов внутри группы neighbors возвращал true
    *
    * @param seq       начальная последовательность, которую нужно разбить на
    *                  группы согласно функции neighbors
    * @param neighbors neighbors возвращает true если значения переданные
    *                  в функции могут быть соседями в одной группе в заданном порядке
    */
  def joiner[A](seq: Seq[A], neighbors: (A, A) => Boolean): Seq[Seq[A]] = {
    val result: List[List[A]] = seq
      .foldLeft[(List[List[A]], A)](List[List[A]](), seq.head) {
      (acc: (List[List[A]], A), current: A) => acc match {
        case (head :: tail, prev) =>
          if (neighbors(prev, current)) ((head :+ current) :: tail, current)
          else (List(current) :: head :: tail, current)
        case (Nil,_) => (List(List(current)), seq.head)
      }
    }._1.foldLeft(List[List[A]]()){(r,c) => c +: r}
    result
  }

  def main(args: Array[String]): Unit = {
    val testSample = Seq(1, 2, 5, 3, 7, 8, 2, 1, 9, 11)
    val expected = Seq(Seq(1, 2, 5), Seq(3, 7, 8), Seq(2), Seq(1, 9, 11))
    println(joiner[Int](testSample, _ < _))
  }
}
//
//=============>
//
//object HelloWorld {
//
//  import scala.concurrent.ExecutionContext.Implicits.global
//  import scala.concurrent.duration.Duration
//  import scala.concurrent.{Await, Future, Promise}
//  import scala.util.{Success,Failure}
//  import scala.concurrent.duration._
//
//  type SimpleService[T] = () => Future[T]
//
//  def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = {
//    val p = Promise[T]()
//    var attempts = attempt
//    do {
//      service().onComplete {
//        case Success(value) => p.trySuccess(value)
//        case Failure(ex) => p.tryFailure(ex)
//      }
//      attempts = attempts - 1
//      Await.ready(service.apply, 1.second)
//    } while(attempts > 0 || !p.isCompleted)
//    p.future
//  }
//
//  def main(args: Array[String]): Unit = {
//
//    def buggyService() =
//      Future {
//        println("Access to master")
//        Thread.sleep(500)
//        if (Math.random() > 0.7) "Done" else scala.sys.error("try again")
//      }
//
//    repeater(buggyService _, 5).onComplete{
//      case Success(value) => print(value)
//      case Failure(ex) => print(ex)
//    }
//  }
//
//}