package catslaboratory.problems

object TwentyOneListTasks extends App {

  /**
   * Task Description:
   * The objective of this Scala program is to demonstrate a simple text compression and decompression technique using
   * run-length encoding (RLE). The program contains two primary methods: 'pack' and 'unpack'.
   *
   * - The 'pack' method takes a List of elements and returns a List of tuples. Each tuple contains an element from
   * the input list and the count of its consecutive occurrences.
   * For example, the input "aabbbca" as a List would be transformed into List(('a', 2), ('b', 3), ('c', 1), ('a', 1)).
   *
   * - The 'unpack' method performs the reverse operation. It takes a List of tuples where each tuple consists of
   * an element and a count. It returns a List of the original elements reconstructed from the counts.
   * For instance, converting List(('a', 2), ('b', 3), ('c', 1), ('a', 1)) back into "aabbbca".
   *
   * This functionality is useful for applications requiring basic data compression and decompression techniques.
   */
  def packFoldLeft[A](xs: List[A]): List[(A, Int)] =
    xs.foldLeft(List.empty[(A, Int)]) { (list: List[(A, Int)], c: A) =>
      list match {
        case x :: xs if x._1 == c => (x._1, x._2 + 1) :: xs
        case _ :: _ => (c, 1) :: list
        case Nil => List((c, 1))
      }
    }.reverse

  def packFoldRight[A](xs: List[A]): List[(A, Int)] = xs match {
    case Nil => Nil
    case _ =>
      xs.foldRight(List.empty[(A, Int)]) { (c, acc) =>
        acc match {
          case (a, count) :: tail if a == c => (a, count + 1) :: tail
          case _ => (c, 1) :: acc
        }
      }
  }

  def pack[A](xs: List[A]): List[(A, Int)] =
    xs match {
      case Nil => Nil
      case y :: ys =>
        pack(ys) match {
          case (c, n) :: cs if y == c => (y, n + 1) :: cs
          case cn :: cns => (y, 1) :: cn :: cns
          case Nil => (y, 1) :: Nil
        }
    }

  def unpack[A](xs: List[(A, Int)]): List[A] =
    xs.flatMap { case (a, count) => List.fill(count)(a) }

  println("abbbca" == unpack(pack("abbbca".toList)).mkString)
  println("" == unpack(pack("".toList)).mkString)
  println("0" == unpack(pack("0".toList)).mkString)

  /**
   * Task 1: Find the most frequent element in a list and remove all its occurrences.
   */
  def mostFrequent[A](xs: List[A]): Option[A] = ???

  def removeMostFrequent[A](xs: List[A]): List[A] = ???

  /**
   * Task 2: Convert strings to ASCII lists and back.
   */
  def stringsToAsciiLists(xs: List[String]): List[List[Int]] = ???

  def asciiListsToStrings(xs: List[List[Int]]): List[String] = ???

  /**
   * Task 3: Calculate the cumulative sum of elements and compute the original list from the cumulative sum.
   */
  def cumulativeSum(xs: List[Int]): List[Int] = ???

  def fromCumulativeSum(xs: List[Int]): List[Int] = ???

  /**
   * Task 4: Find the difference between the maximum and minimum elements, and find elements closest to the mean.
   */
  def maxMinDiff(xs: List[Int]): Int = ???

  def closestToMean(xs: List[Int]): List[Int] = ???

  /**
   * Task 5: Check if a list is a palindrome and generate the shortest palindrome by adding elements at the end.
   */
  def isPalindrome[A](xs: List[A]): Boolean = ???

  def makePalindrome[A](xs: List[A]): List[A] = ???

  /**
   * Task 6: Remove duplicates and create a list of duplicates.
   */
  def removeDuplicates[A](xs: List[A]): List[A] = ???

  def listDuplicates[A](xs: List[A]): List[A] = ???

  /**
   * Task 7: Reverse a list and verify if the reversed list is equal to the original.
   */
  def reverseList[A](xs: List[A]): List[A] = ???

  def isReversible[A](xs: List[A], reversed: List[A]): Boolean = ???

  /**
   * Task 8: Count occurrences of each element and find elements with exact occurrences.
   */
  def countOccurrences[A](xs: List[A]): Map[A, Int] = ???

  def elementsWithCount[A](xs: List[A], count: Int): List[A] = ???

  /**
   * Task 9: Find the second largest element and remove it from the list.
   */
  def secondLargest(xs: List[Int]): Option[Int] = ???

  def removeSecondLargest(xs: List[Int]): List[Int] = ???

  /**
   * Task 10: Collect elements that are powers of two and generate the next power of two.
   */
  def powersOfTwo(xs: List[Int]): List[Int] = ???

  def nextPowerOfTwo(x: Int): Int = ???

  /**
   * Task 11: Multiply all elements and calculate individual contributions to the product.
   */
  def product(xs: List[Int]): Int = ???

  def contributionsToProduct(xs: List[Int], product: Int): List[Int] = ???

  /**
   * Task 12: Remove all occurrences of a specified element and verify if the list still contains any element.
   */
  def removeAll[A](xs: List[A], elem: A): List[A] = ???

  def containsElement[A](xs: List[A], elem: A): Boolean = ???

  /**
   * Task 13: Find the smallest difference and identify pairs of elements with this difference.
   */
  def smallestDifference(xs: List[Int]): Int = ???

  def pairsWithSmallestDifference(xs: List[Int], diff: Int): List[(Int, Int)] = ???

  /**
   * Task 14: Zip two lists and unzip them back to original lists.
   */
  def zipLists[A, B](xs: List[A], ys: List[B]): List[(A, B)] = ???

  def unzipLists[A, B](zs: List[(A, B)]): (List[A], List[B]) = ???

  /**
   * Task 15: Filter even numbers and verify if all numbers in the result are even.
   */
  def filterEvens(xs: List[Int]): List[Int] = ???

  def allEvens(xs: List[Int]): Boolean = ???

  /**
   * Task 16: Sort strings by length and shuffle them to original randomness.
   */
  def sortByLength(xs: List[String]): List[String] = ???

  def shuffleToOriginal(xs: List[String], original: List[String]): List[String] = ???

  /**
   * Task 17: Convert integers to strings and parse them back to integers.
   */
  def convertToStrings(xs: List[Int]): List[String] = ???

  def parseToInts(xs: List[String]): List[Int] = ???

  /**
   * Task 18: Generate all permutations and count the number of unique permutations.
   */
  def permutations[A](xs: List[A]): List[List[A]] = ???

  def countUniquePermutations[A](xs: List[List[A]]): Int = ???

  /**
   * Task 19: Combine elements into a single string and split them back into a list.
   */
  def mkString[A](xs: List[A], sep: String): String = ???

  def splitBySeparator(s: String, sep: String): List[String] = ???

  /**
   * Task 20: Group elements by type and count elements in each group.
   */
  def groupByType(xs: List[Any]): Map[String, List[Any]] = ???

  def countInGroups(groups: Map[String, List[Any]]): Map[String, Int] = ???


}


