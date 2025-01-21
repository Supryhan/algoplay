package catslaboratory.problems

object MinTwoInt extends App {
  def findTwoSmallestNumbers(numbers: List[Int]): (Int, Int) = { // we return (min1,min2) where: min1 > min2
    @scala.annotation.tailrec
    def inner(a1: Int, a2: Int, tail: List[Int]): (Int, Int) = {
      tail match {
        case x1 :: x2 :: xs => {
          if (x1 < a1 && x1 < a2) {
            inner(a2, x1, x2 :: xs)
          }
          else if (x1 < a1 && x1 > a2) {
            inner(x1, a2, x2 :: xs)
          }
          else {
            inner(a1, a2, x2 :: xs)
          }
        }
        case x :: _ => {
          if (x < a1 && x < a2) {
            (a2, x)
          }
          else if (x < a1 && x > a2) {
            (x, a2)
          }
          else {
            (a1, a2)
          }
        }
      }
    }

    inner(Int.MaxValue, Int.MaxValue, numbers)
  }

  println(findTwoSmallestNumbers(List(7, 2, 33, 4, 5, 6)))
  //(4,2)
  println(findTwoSmallestNumbers(List(7, Int.MaxValue, 33, Int.MaxValue, 5, 6)))
  //(6,5)
  println(findTwoSmallestNumbers(List(Int.MaxValue, Int.MaxValue)))
  (2147483647, 2147483647)
  println(findTwoSmallestNumbers(List(Int.MaxValue, 0, -124)))
  //(0,-124)

}
