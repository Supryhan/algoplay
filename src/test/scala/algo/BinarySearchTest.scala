package algo

import implicits.ExtendAnyVal.DoubleOps
import org.specs2.mutable.Specification

class BinarySearchTest extends Specification {

  "A binary search should give correct result" >> {

    val arr = Array[Int](0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)

    BinarySearch.find(Integer.MIN_VALUE, arr) must_== None
    BinarySearch.find(1, arr) must_== Some(1)
    BinarySearch.find(-1, arr) must_== None
    BinarySearch.find(1024, arr) must_== None
    BinarySearch.find(13, arr) must_== Some(13)
    BinarySearch.find(Integer.MAX_VALUE, arr) must_== None
  }
}
