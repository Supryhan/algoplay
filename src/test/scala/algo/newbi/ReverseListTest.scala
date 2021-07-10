package algo.newbi

import org.specs2.mutable.Specification

class ReverseListTest extends Specification {
  val rl = new ReverseList
  "Revers List" should {
    "reverse correctly if not empty list" in {
      rl.solution(List(1,2,3)) must_== List(3,2,1)
    }
    "reverse correctly if empty list" in {
      rl.solution(List()) must_== Nil
    }
  }
}
