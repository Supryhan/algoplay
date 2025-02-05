package problemslab.leetcode

import scala.annotation.tailrec

object P69Sqrt extends App {

  //  private def solution(n: Int): Int = {

  //    @tailr'ec
  //    def inner(exp: Int): Int = {
  //      val y = exp * exp
  //      val r = (exp + 1) * (exp + 1)
  //      if(y == n) exp
  //      else if( r > n) exp
  //      else inner(exp + 1)
  //    }
  //
  //    n match {
  //      case 0 => 0
  //      case 1 => 1
  //      case _: Int => inner(1)
  //    }
  //  }'

  def solution(x: Int): Int = {
    if (x < 2) return x

    var left = 1
    var right = x / 2
    while (left <= right) {
      val mid = left + (right - left) / 2
      val midSquared = mid.toLong * mid.toLong
      if (midSquared == x) return mid
      else if (midSquared < x) left = mid + 1
      else right = mid - 1
    }
    right
  }


  println(s"For number 0: ${solution(0)} and expected is 0. ${solution(0) == 0}")
  println(s"For number 1: ${solution(1)} and expected is 1. ${solution(1) == 1}")
  println(s"For number 2: ${solution(2)} and expected is 1. ${solution(2) == 1}")
  println(s"For number 3: ${solution(3)} and expected is 1. ${solution(3) == 1}")
  println(s"For number 4: ${solution(4)} and expected is 2. ${solution(4) == 2}")
  println(s"For number 5: ${solution(5)} and expected is 2. ${solution(5) == 2}")
  println(s"For number 8: ${solution(8)} and expected is 2. ${solution(8) == 2}")
  println(s"For number 9: ${solution(9)} and expected is 3. ${solution(9) == 3}")
  println(s"For number 36: ${solution(36)} and expected is 6. ${solution(36) == 6}")
  println(s"For number 37: ${solution(37)} and expected is 6. ${solution(37) == 6}")
  println(s"For number 49: ${solution(49)} and expected is 7. ${solution(49) == 7}")
  println(s"For number 55: ${solution(55)} and expected is 7. ${solution(55) == 7}")
  println(s"For number 2147483647: ${solution(2147483647)} and expected is ${solution(2147483647) == 46340}")

}
