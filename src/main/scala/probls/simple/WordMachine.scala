package probls.simple

object WordMachine extends App {

  private val MIN = 0
  private val MAX = 0x7FFF_FFFF
  private val NumericPattern = "\\d+".r

  type Stack = List[Int]
  type Result[T] = Either[String, T]

  val userInput = "1 1 + 2 + DUP +"
  println(process(userInput))

  def process(input: String): Result[Int] =
    input
      .split(" ")
      .toList
      .foldLeft[Result[Stack]](Right(List.empty[Int])) {
        (stack, token) => apply(stack, token)
      }.map(_.head)

  private def apply(stack: Result[Stack], token: String): Result[Stack] = token match {
    case NumericPattern() => stack.flatMap(stack => push(stack, token.toInt))
    case "POP" => stack.flatMap(stack => pop(stack))
    case "DUP" => stack.flatMap(stack => dup(stack))
    case "+" => stack.flatMap(stack => add(stack))
    case "-" => stack.flatMap(stack => sub(stack))
    case _ => Left(s"Unknown operation: $token")
  }

  private def push(state: Stack, value: Int): Result[Stack] =
    withinRange(value).map(_.head :: state)

  private def pop(stack: Stack): Result[Stack] =
    stack match {
      case _ :: tail => Right(tail)
      case Nil => Left("Too few elements are available")
    }

  private def dup(stack: Stack): Result[Stack] =
    stack match {
      case head :: tail => Right(head :: head :: tail)
      case Nil => Left("Too few elements are available")
    }

  private def add(stack: Stack): Result[Stack] =
    stack match {
      case a :: b :: _ => Right(stack).flatMap(pop).flatMap(pop).flatMap(push(_: Stack, a + b))
      case _ => Left("Too few elements are available")
    }

  private def sub(stack: Stack): Result[Stack] =
    stack match {
      case a :: b :: _ => Right(stack).flatMap(pop).flatMap(pop).flatMap(push(_: Stack, a - b))
      case _ => Left("Too few elements are available")
    }

  private def withinRange(value: Int): Result[Stack] = Either
    .cond(value >= MIN && value <= MAX, List(value), "Number is out of integer range")

}
