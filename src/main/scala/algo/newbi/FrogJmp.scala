package algo.newbi

class FrogJmp {
  def solution(x: Int, y: Int, d: Int): Int = {
    math.ceil({
      y - x
    }.toDouble / d).toInt
  }
}