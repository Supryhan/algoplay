package problemslab.future

object FutureListProcessing extends App {

  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future

  def f1(i: Int) = Future(i)

  def f2(i: Int) = Future {
    if (i % 2 == 0) throw new Exception else i
  }

  val l = List(1, 2, 3)

  val results = Future.sequence(l.map { i =>
    val f = for {
      r1 <- f1(i)
      r2 <- f2(i)
    } yield Some(r1)

    f.recoverWith {
      case e => Future {
        println("Called recover " + i); None
      }
    }
  })
  results onComplete println

  Thread.sleep(1000)
}
