package com.supryhan.catslaboratory.problems

import scala.annotation.tailrec

object GcdProblem extends App {

  /**
   * Computes the greatest common divisor (GCD) of two non-negative integers
   * using the recursive Euclidean algorithm.
   *
   * The algorithm works as follows:
   * 1. If q is equal to 0, return p.
   * 2. Otherwise, calculate the remainder of p divided by q.
   * 3. Recursively compute the GCD of q and the remainder.
   *
   * Examples:
   * gcd(48, 18) should return 6.
   * gcd(25, 10) should return 5.
   * gcd(7, 0) should return 7.
   *
   * @param p the first non-negative integer
   * @param q the second non-negative integer
   * @return the greatest common divisor of p and q
   */
  def gcdByTailRec(p: Int, q: Int): Int = {

    @tailrec
    def loop(p: Int, q: Int): Int = {
      if (q == 0) p
      else loop(q, p % q)
    }

    loop(p, q)
  }

  def gcdByLazyList(p: Int, q: Int): Int =
    LazyList
      .iterate((p, q)) {
        case (a, b) => (b, a % b)
      }
      .dropWhile {
        case (_, b) => b != 0
      }
      .take(1)
      .head._1

  assert(6 == gcdByTailRec(48, 18))
  assert(6 == gcdByLazyList(48, 18))
}
