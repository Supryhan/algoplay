package problems.leetcode

object Roman2Integer {
  def romanToInt(s: String): Int = {
    val mapping = Map(
      'I' -> 1,
      'V' -> 5,
      'X' -> 10,
      'L' -> 50,
      'C' -> 100,
      'D' -> 500,
      'M' -> 1000)

    s.toList.foldLeft((0, 0)){(t: (Int, Int), c: Char) => {
      val current = mapping(c)
      val summAccumulated = t._1 + current
      if((t._2 == 1 && (current == 5 || current == 10))
        || (t._2 == 10 && (current == 50 || current == 100))
        || (t._2 == 100 && (current == 500 || current == 1000)))
        (summAccumulated - 2 * t._2, 0)
      else if(current == 1 || current == 10 || current == 100)
        (summAccumulated, current)
      else
        (summAccumulated, 0)
    }}._1
  }
}
