package catslaboratory.problems


// Task Description:
// Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals,
// and return an array of the non-overlapping intervals that cover all the intervals in the input.
//
// Example:
// Input: Intervals = [(1,4),(4,5)]
// Output: [(1,5)]
// Explanation: Intervals '1,4' and '4,5' are considered overlapping.
//
// Constraints:
// 1 <= intervals.length <= 104
// intervals[i].length == 2
// 0 <= starti <= endi <= 104

object IntervalsProblem extends App {

  private def solution(list: List[Tuple2[Int, Int]]): List[Tuple2[Int, Int]] = {

    list.foldLeft(List.empty[Tuple2[Int, Int]]) {
      (acc, a) =>
        a match {
          case (start, finish) => {
            acc match {
              case x :: xs => {
                if (x._2 >= start) List((x._1, finish)) ::: xs
                else a :: x :: xs
              }
              case Nil => (start, finish) :: acc
            }
          }
        }
    }.reverse
  }

  val list: List[Tuple2[Int, Int]] = List((1, 3), (2, 6), (8, 10), (15, 18))

  println(s"Merged intervals: ${solution(list)}")
}

