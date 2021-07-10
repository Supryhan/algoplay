package algo.newbi

import org.specs2.mutable.Specification

class FrogJmpTest extends Specification {
  val fj: FrogJmp = new FrogJmp
  "A Frog" should {
    "jump as expected" in {
      fj.solution(10, 85, 30) must_== 3
    }
    "jump 1 to 10" in {
      fj.solution(1, 10, 1) must_== 9
    }
    "jump minimum" in {
      fj.solution(100, 100, 30) must_== 0
    }
    "jump max" in {
      fj.solution(10, 100500, 30) must_== 3350
    }
    "jump extremely max" in {
      fj.solution(1, 1100500000, 1) must_== 1100499999
    }
    "jump from min x" in {
      fj.solution(1, 30, 30) must_== 1
    }
  }
}
