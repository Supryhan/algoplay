package problemslab.leetcode

object P27RemoveElement extends App {
  def removeElement(nums: Array[Int], `val`: Int): Int = {
    nums.foldLeft(0) {
      case (index, value) if value != `val` =>
        nums(index) = value
        index + 1
      case (index, _) => index
    }
  }


  val r = Array(1, 2, 3, 4, 5, 8, 4, 3, 2)
  r.foreach(q => print(q + ", "))
  println()
  println(removeElement(r, 2))
  r.foreach(q => print(q + ", "))
}
