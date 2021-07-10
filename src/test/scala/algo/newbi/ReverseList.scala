package algo.newbi

import scala.annotation.tailrec

class ReverseList {
  def solution(list: List[Int]): List[Int] = {
    val l: List[Int] = Nil
    @tailrec
    def inn(res: List[Int], tail: List[Int]): List[Int] = {
      tail match {
        case x :: xs => inn((x :: res), xs)
        case Nil =>  res
      }
    }
    inn(l,list)
  }
}
