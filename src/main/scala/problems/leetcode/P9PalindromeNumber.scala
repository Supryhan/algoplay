package problems.leetcode

import scala.annotation.tailrec

object P9PalindromeNumber {

  //1
  def isPalindrome(x: Int): Boolean =
    if(x < 0) false
    else if(x <= 9) true
    else x == invert(x, exponent(x))

  def invert(num: Int, e: Int): Int =
    if(e == 1) num
    else (num % 10) * e + invert(num / 10, e / 10)

  @tailrec
  def exponent(num: Int, i: Int = 1): Int =
    if((num / 10) == 0) i
    else exponent(num / 10, i * 10)

  //2
  def isPalindrome2(x: Int): Boolean =
    Option.when(x >= 0)(reverse(x, 0)).fold(false)(x == _)

  def reverse(input: Int, result: Int): Int =
    Option.when(input > 0)(reverse(input/10, result*10+input%10))
      .getOrElse(result)

}
