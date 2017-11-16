package tmp

object CollectionExperiments extends App {

  val strings = Seq("1", "2", "foo", "3", "bar")

  def toInt(str: String): Option[Integer] = {
    try {
      Some(str.toInt)
    }
    catch {
      case e: Exception => None
    }
  }

  println(strings.map(toInt))
  println(strings.flatMap(toInt))
  val sum =strings.flatMap(toInt).sum
  print(sum)
  println(List(1, 2, 3).sum)

  val row0 = Seq(1, 2, 3)
  val row1 = Seq(4, 5, 6)
  val row2 = Seq(7, 8, 9)
  val row3 = Seq(10, 11, 12)
  val row4 = Seq(13, 14, 15)
  val row5 = Seq(16, 17, 18)
  val row6 = Seq(19, 20, 21)
  val row7 = Seq(22, 23, 24)
  val row8 = Seq(25, 26, 27)
  val slice0 = Seq(row0, row1, row2)
  val slice1 = Seq(row3, row4, row5)
  val slice2 = Seq(row6, row7, row8)
  val cube = Seq(slice0, slice1, slice2)
  for (slice <- cube; row <- slice; item <- row) print(item + " ")
  println()
  cube.foreach(slice => slice.foreach(row => row.foreach(print(_))))
}
