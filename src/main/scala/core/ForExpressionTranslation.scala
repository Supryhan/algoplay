package core

import scala.collection.WithFilter

object ForExpressionTranslation extends App {

  case class Wrapper(value: String) extends AnyVal

  val forUntil = for {
    i <- 1 until 5
    j <- 1 until i
    if (i + j) % 2 == 0
  } yield (i, j)

  val flatMapUntil = {
    val e1: Range = (1 until 5)
    e1.flatMap {
      i =>
        val e2: Range = (1 until i)
        e2.withFilter {
          j =>
            (i + j) % 2 == 0
        }.map(k => (i, k))
    }
  }
  println(forUntil)
  println(flatMapUntil)


  val forSeq = for {
    i <- Seq(1, 2, 3, 5)
    j <- List(1, 2, 5, 7)
    if (i + j) % 2 == 0
    Wrapper(str) <- Option(Wrapper(s"abc$j"))
  } yield List(i, j, str)

  val flatMapSeq =
    Seq(1, 2, 3, 5)
      .flatMap(i => {
        List(1, 2, 5, 7)
          .withFilter(j => (i + j) % 2 == 0)
          .flatMap(j => {
            Option(Wrapper(s"abc$j").value)
              .map(str =>
                List(i, j, str))
          })
      })
  println(forSeq)
  println(flatMapSeq)

  println(for (i <- Some(42)) yield i)

}
