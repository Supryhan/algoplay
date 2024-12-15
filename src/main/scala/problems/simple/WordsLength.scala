package problems.simple

object WordsLength extends App {

  val loremIpsumText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."

  val wordsList: Array[String] = loremIpsumText.split("\\s+")

  val array = wordsList.mkString(", ")
  println(array)

  def wordsToPairs(arr: List[String]): List[(String, Int)] = {
    val processed = arr.map(_.trim)
    val dist = processed.distinct
    val pairs = dist.map(w => (w, w.length))
    pairs.sortBy(x => x._2)
  }

  println(wordsToPairs(List.from(wordsList)))


}
