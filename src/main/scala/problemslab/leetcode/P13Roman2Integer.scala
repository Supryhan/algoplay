package problemslab.leetcode

object P13Roman2Integer extends App {
  def romanToInt(s: String): Int = {
    val mapping = Map(
      'I' -> 1,
      'V' -> 5,
      'X' -> 10,
      'L' -> 50,
      'C' -> 100,
      'D' -> 500,
      'M' -> 1000)

    s.map(mapping).foldLeft((0, 0)){(t: (Int, Int), c: Int) => {
      val summ = t._1 + c
      if((t._2 == 1 && (c == 5 || c == 10))
        || (t._2 == 10 && (c == 50 || c == 100))
        || (t._2 == 100 && (c == 500 || c == 1000)))
        (summ - 2 * t._2, 0)
      else if(c == 1 || c == 10 || c == 100)
        (summ, c)
      else
        (summ, 0)
    }}._1
  }
}
