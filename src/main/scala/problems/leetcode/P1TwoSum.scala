package problems.leetcode

object P1TwoSum {
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {

    if(nums.length < 2)
      Array.empty[Int]

    for (i <- 0 to nums.length - 2) {
      val diff = target - nums(i)
      for (j <- i + 1 until nums.length)
        if(diff == nums(j)) return Array(i, j)
    }

    Array.empty[Int]
  }
}
