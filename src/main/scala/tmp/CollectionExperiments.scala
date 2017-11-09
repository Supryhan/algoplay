package tmp

object CollectionExperiments extends App {

  val strings = Seq("1", "2", "foo", "3", "bar")

  def toInt(val string: String): Option[Integer] = {
    try {}
    catch {}
  }
  strings.map(toInt)
  strings.flatMap(toInt)
  strings.flatMap(toInt).sum
}
