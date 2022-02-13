package core

object OptionChaining extends App {

  def noneInAnyCase(b: Boolean): Option[Boolean] = None

  def isLengthGT(length: Int, input: String): Option[Boolean] =
    Option.when(input.length > length)(true).flatMap(noneInAnyCase).orElse(Some(false))

  println(s"Result: ${isLengthGT(5, "word")}")
}
