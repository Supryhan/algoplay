package algo

import scala.annotation.tailrec

object BinarySearch extends App {
  println(find(16, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
  println(find(Integer.MAX_VALUE, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
  println(find(-100, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
  println(find(Integer.MIN_VALUE, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
  println(find(6, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
  println(find(0, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))
  println(find(13, Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)))

  def find(n: Int, inArray: Array[Int]): Option[Int] = {
    @tailrec def binary_sort(start: Int, end: Int): Option[Int] = {
      if (start > end) None
      val pivot = start + (end - start) / 2
      inArray(pivot) compareTo n match {
        case 0 => Some(pivot)
        case -1 => binary_sort(pivot + 1, end)
        case 1 => binary_sort(start, pivot - 1)
        case _ => None
      }
    }

    if (n < inArray.head || n > inArray.last) {
      return None
    }
    binary_sort(0, inArray.length - 1)
  }
}
