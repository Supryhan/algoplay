package probls.leetcode

object P26RemoveDuplicatesFromSortedArray extends App {
  def removeDuplicates(nums: Array[Int]): Int = {
    var k = 0
    var pointer = 1
    while(pointer < nums.length) {
      if(nums(k) != nums(pointer)) {
        nums.update(k + 1, nums(pointer))
        k = k + 1
        pointer = pointer + 1
      } else {
        pointer = pointer + 1
      }
    }
    nums.foreach(i => print(s"$i, "))
    k + 1
  }


  println(s"${removeDuplicates(List(8, 3, 2, 2, 1).toArray)}")


}
