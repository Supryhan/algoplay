package algo

import scala.language.reflectiveCalls

object AlgoSum extends App {

}

object Solution {

  //  def simpleArraySum(n: Int, ar: Array[Int]): Int = ar.foldLeft(0)((a, b) => a + b)
  def simpleArraySum(n: Int, ar: Array[Int]): Int = ar.sum

  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in)
    var n = sc.nextInt()
    var ar = new Array[Int](n)
    for (ar_i <- 0 until n) {
      ar(ar_i) = sc.nextInt()
    }
    val result = simpleArraySum(n, ar)
    println(result)
  }
}

object Solution2 {
  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in)
    val a0 = sc.nextInt()
    val a1 = sc.nextInt()
    val a2 = sc.nextInt()
    val b0 = sc.nextInt()
    val b1 = sc.nextInt()
    val b2 = sc.nextInt()
    val result = comparing((a0, a1, a2), (b0, b1, b2))
    print(s"${result._1} ${result._2}")
  }

  def comparing(a: Tuple3[Int, Int, Int], b: Tuple3[Int, Int, Int]): Tuple2[Int, Int] = {
    import Bool._
    (((a._1 > b._1) ? 1 | 0) + ((a._2 > b._2) ? 1 | 0) + ((a._3 > b._3) ? 1 | 0),
      ((a._1 < b._1) ? 1 | 0) + ((a._2 < b._2) ? 1 | 0) + ((a._3 < b._3) ? 1 | 0))
  }

  case class Bool(b: Boolean) {
    def ?[X](k: => X) = new {
      def |(m: => X) = if (b) k else m
    }
  }

  object Bool {
    implicit def Boolean2Bool(b: Boolean): Bool = Bool(b)
  }

}

object Solution3 extends App {

  case class Tree[+T](value: T, left: Option[Tree[T]], right: Option[Tree[T]])

  var queue = collection.mutable.Queue[Option[Tree[Char]]]()
  val root: Option[Tree[Char]] = Some(Tree('a',
                                           Some(Tree('b',
                                                     Some(Tree('d',
                                                               None,
                                                               None)),
                                                     Some(Tree('e',
                                                               None,
                                                               None)))),
                                           Some(Tree('c',
                                                     Some(Tree('f',
                                                               None,
                                                               None)),
                                                     Some(Tree('g',
                                                               None,
                                                               None))))))
  queue += root

  def m(): Unit = {
    val t: Option[Tree[Char]] = queue.dequeue()
    var left: Boolean = false
    var right: Boolean = false
    if (t.isDefined && t.get.left.isDefined) {
      queue :+ t.get.left;
      left = true
    }
    if (t.isDefined && t.get.right.isDefined) {
      queue :+ t.get.right;
      right = true
    }
    if (queue.nonEmpty) m()
    print(t.get.value)
    if (left) print(t.get.left.get.value)
    if (right) print(t.get.right.get.value)
  }

  m()
}

object Solution4 {

  def main(args: Array[String]) {
    val sc = new java.util.Scanner(System.in)
    //    print("enter experiments:")
    val t = sc.nextInt()
    var a0: Int = 0
    var list: List[String] = List.empty[String]
    while (a0 < t) {
      var tempList: List[Int] = List.empty[Int]
      //      print("students,min:")
      val condition: Tuple2[Int, Int] = (sc.nextInt(), sc.nextInt())
      //      print("timings:")
      for (i <- 1 to condition._1; a = sc.nextInt(); if a <= 0) tempList = a :: tempList
      list = if (tempList.length >= condition._2) "NO" :: list else "YES" :: list
      a0 += 1
    }
    list.reverse.foreach(println)
  }
}
