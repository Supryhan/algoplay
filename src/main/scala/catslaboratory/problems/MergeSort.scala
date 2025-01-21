package catslaboratory.problems

object MergeSort extends App {

  def msort[T](cond: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (cond(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (left, right) = xs splitAt n
      merge(msort(cond)(left), msort(cond)(right))
    }
  }

  private val mergeSortIntAsc = msort((x: Int, y: Int) => x < y) _
  private val result = mergeSortIntAsc(9 :: 1 :: 8 :: 3 :: 2 :: Nil)
  println(result.toString)

}
