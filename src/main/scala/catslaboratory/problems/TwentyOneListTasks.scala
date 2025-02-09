package catslaboratory.problems

import cats.data.OptionT

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
  def packFoldLeft[A](list: List[A]): List[(A, Int)] =
    list.foldLeft(List.empty[(A, Int)]) { (list: List[(A, Int)], c: A) =>
      list match {
        case x :: list if x._1 == c => (x._1, x._2 + 1) :: list
        case _ :: _ => (c, 1) :: list
        case Nil => List((c, 1))
      }
    }.reverse

  def packFoldRight[A](list: List[A]): List[(A, Int)] = list match {
    case Nil => Nil
    case _ =>
      list.foldRight(List.empty[(A, Int)]) { (c, acc) =>
        acc match {
          case (a, count) :: tail if a == c => (a, count + 1) :: tail
          case _ => (c, 1) :: acc
        }
      }
  }

  def pack[A](list: List[A]): List[(A, Int)] =
    list match {
      case Nil => Nil
      case y :: ys =>
        pack(ys) match {
          case (c, n) :: cs if y == c => (y, n + 1) :: cs
          case cn :: cns => (y, 1) :: cn :: cns
          case Nil => (y, 1) :: Nil
        }
    }

  def unpack[A](list: List[(A, Int)]): List[A] =
    list.flatMap { case (a, count) => List.fill(count)(a) }

  println("abbbca" == unpack(pack("abbbca".toList)).mkString)
  println("" == unpack(pack("".toList)).mkString)
  println("0" == unpack(pack("0".toList)).mkString)

  /**
   * Task 1: Find the most frequent element in a list and remove all its occurrences.
   */
  def mostFrequent[A](list: List[A]): Option[A] = {
    list.groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap
      .maxByOption(_._2)
      .map(_._1)
  }

  println("mostFrequent:")
  println(Some('q') == mostFrequent("abbbqqqqca".toList))
  println(Some('b') == mostFrequent("abbbca".toList))
  println(None == mostFrequent("".toList))
  println(Some('0') == mostFrequent("0".toList))

  def removeMostFrequent[A](list: List[A]): List[A] = {
    val mostFrequent = list.groupBy(identity).maxByOption(_._2.size).map(_._1)
    list filterNot mostFrequent.contains
  }

  println("removeMostFrequent:")
  println("abbbca".toList == removeMostFrequent("abbbqqqqca".toList))
  println("aca".toList == removeMostFrequent("abbbca".toList))
  println("".toList == removeMostFrequent("".toList))
  println("".toList == removeMostFrequent("0".toList))

  /**
   * Task 2: Convert strings to ASCII lists and back.
   */
  def stringsToAsciiLists(list: List[String]): List[List[Int]] = list.map(str => str.toList.map(c => c.toInt))

  def asciiListsToStrings(list: List[List[Int]]): List[String] = list.map(list => list.map(i => i.toChar).mkString)

  println(s"LOREM: ${
    stringsToAsciiLists(
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
    eiusmod tempor incididunt ut labore et dolore magna aliqua.""".split(" ").toList)
  }")
  println(s"DE:)LOREM: ${
    asciiListsToStrings(stringsToAsciiLists(
      """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
        eiusmod tempor incididunt ut labore et dolore magna aliqua.""".split(" ").toList)).mkString(" ")
  }")

  /**
   * Task 3: Calculate the cumulative sum of elements and compute the original list from the cumulative sum.
   */
  def cumulativeSum(list: List[Int]): List[Int] =
    list.foldLeft(List.empty[Int])((acc, e) => acc match {
      case y :: _ => e + y :: acc
      case Nil => e :: acc
    }).reverse

  def fromCumulativeSum(list: List[Int]): List[Int] = {
    list.foldLeft(
      (List.empty[Int], 0)
    )(
      (t, e) =>
        t._1 match {
          case _ :: _ => (e - t._2 :: t._1, e)
          case Nil => (e :: Nil, e)
        }
    )._1.reverse
  }

  println(s"cumulativeSum: ${cumulativeSum(List(0, 1, 2, 3, 4, 5, 6))}")
  println(s"fromCumulativeSum: ${fromCumulativeSum(cumulativeSum(List(0, 1, 2, 3, 4, 5, 6)))}")

  /**
   * Task 4: Find the difference between the maximum and minimum elements, and find elements closest to the mean.
   */
  def maxMinDiff(list: List[Int]): Option[Int] = {
    list match {
      case Nil => None
      case _ :: Nil => Some(0)
      case x :: xs =>
        val t: (Int, Int) = xs.foldLeft((x, x)) { // val acc = (min, max)
          (acc: (Int, Int), e: Int) =>
            if (e <= acc._1) (e, acc._2)
            else if (e >= acc._2) (acc._1, e)
            else acc
        }
        Some(t._2 - t._1)
    }
  }

  println(s"maxMinDiff: ${maxMinDiff(List(-100, 127, 2, -7, 42, -128)).mkString}")
  println(s"maxMinDiff: ${maxMinDiff(List(1, 2, 3)).mkString}")
  println(s"maxMinDiff: ${maxMinDiff(List(1)).mkString}")
  println(s"maxMinDiff: ${maxMinDiff(List()).mkString}")
  println(s"maxMinDiff: ${maxMinDiff(Nil).mkString}")

  def closestToMean(list: List[Int]): OptionT[List, Int] = ???

  /**
   * Task 5: Check if a list is a palindrome and generate the shortest palindrome by adding elements at the end.
   */
  def isPalindrome[A](list: List[A]): Boolean = ???

  def makePalindrome[A](list: List[A]): List[A] = ???

  /**
   * Task 6: Remove duplicates and create a list of duplicates.
   */
  def removeDuplicates[A](list: List[A]): List[A] = ???

  def listDuplicates[A](list: List[A]): List[A] = ???

  /**
   * Task 7: Reverse a list and verify if the reversed list is equal to the original.
   */
  def reverseList[A](list: List[A]): List[A] = ???

  def isReversible[A](list: List[A], reversed: List[A]): Boolean = ???

  /**
   * Task 8: Count occurrences of each element and find elements with exact occurrences.
   */
  def countOccurrences[A](list: List[A]): Map[A, Int] = ???

  def elementsWithCount[A](list: List[A], count: Int): List[A] = ???

  /**
   * Task 9: Find the second largest element and remove it from the list.
   */
  def secondLargest(list: List[Int]): Option[Int] = ???

  def removeSecondLargest(list: List[Int]): List[Int] = ???

  /**
   * Task 10: Collect elements that are powers of two and generate the next power of two.
   */
  def powersOfTwo(list: List[Int]): List[Int] = ???

  def nextPowerOfTwo(x: Int): Int = ???

  /**
   * Task 11: Multiply all elements and calculate individual contributions to the product.
   */
  def product(list: List[Int]): Int = ???

  def contributionsToProduct(list: List[Int], product: Int): List[Int] = ???

  /**
   * Task 12: Remove all occurrences of a specified element and verify if the list still contains any element.
   */
  def removeAll[A](list: List[A], elem: A): List[A] = ???

  def containsElement[A](list: List[A], elem: A): Boolean = ???

  /**
   * Task 13: Find the smallest difference and identify pairs of elements with this difference.
   */
  def smallestDifference(list: List[Int]): Int = ???

  def pairsWithSmallestDifference(list: List[Int], diff: Int): List[(Int, Int)] = ???

  /**
   * Task 14: Zip two lists and unzip them back to original lists.
   */
  def zipLists[A, B](list: List[A], ys: List[B]): List[(A, B)] = ???

  def unzipLists[A, B](zs: List[(A, B)]): (List[A], List[B]) = ???

  /**
   * Task 15: Filter even numbers and verify if all numbers in the result are even.
   */
  def filterEvens(list: List[Int]): List[Int] = ???

  def allEvens(list: List[Int]): Boolean = ???

  /**
   * Task 16: Sort strings by length and shuffle them to original randomness.
   */
  def sortByLength(list: List[String]): List[String] = ???

  def shuffleToOriginal(list: List[String], original: List[String]): List[String] = ???

  /**
   * Task 17: Convert integers to strings and parse them back to integers.
   */
  def convertToStrings(list: List[Int]): List[String] = ???

  def parseToInts(list: List[String]): List[Int] = ???

  /**
   * Task 18: Generate all permutations and count the number of unique permutations.
   */
  def permutations[A](list: List[A]): List[List[A]] = ???

  def countUniquePermutations[A](list: List[List[A]]): Int = ???

  /**
   * Task 19: Combine elements into a single string and split them back into a list.
   */
  def mkString[A](list: List[A], sep: String): String = ???

  def splitBySeparator(s: String, sep: String): List[String] = ???

  /**
   * Task 20: Group elements by type and count elements in each group.
   */
  def groupByType(list: List[Any]): Map[String, List[Any]] = ???

  def countInGroups(groups: Map[String, List[Any]]): Map[String, Int] = ???


}


