package catslaboratory.monadslab

import cats.data.State

object StateMonadPractice extends App {

  case class ShoppingCart(items: List[String], total: Double)


  def addToCart(item: String, price: Double): State[ShoppingCart, Double] = State {
    (cart: ShoppingCart) =>
      (ShoppingCart(item :: cart.items, cart.total + price), price + cart.total)
  }

  private val shoppingResult = for {
    _ <- addToCart("boots", 120)
    _ <- addToCart("hat", 80)
    total <- addToCart("shirt", 10)
  } yield total

  private val actualResult = s"Total: ${shoppingResult.run(ShoppingCart(Nil, 0)).value._2}"
  assert("Total: 210.0" == actualResult)
  println(actualResult)

  // returns a State data structure that, when run, will not change the state but will issue the value f(a)
  def inspect[A, B](f: A => B): State[A, B] = State(a => (a, f(a)))

  val expectedI = s"Inspect: ${inspect[String, String](a => a).run("x").value.toString}"
  assert("Inspect: (x,x)" == expectedI)
  println(expectedI)

  // returns a State data structure that, when run, returns the value of that state and makes no changes
  def get[A]: State[A, A] = State(a => (a, a))

  val expectedG = s"Get: ${get[String].run("x").value._2}"
  assert("Get: x" == expectedG)
  println(expectedG)

  // returns a State data structure that, when run, returns Unit and sets the state to that value
  def set[A](value: A): State[A, Unit] = State(_ => (value, ()))

  val expectedS = s"Set: ${get[String].run("x").value._1}"
  assert("Set: x" == expectedS)
  println(expectedS)

  // returns a State data structure that, when run, will return Unit and sets the state to f(state)
  def modify[A](f: A => A): State[A, Unit] = State(a => (f(a), ()))

  val expectedU = s"Modify: ${modify[String](a => a).run("x").value._2.toString}"
  assert("Modify: ()" == expectedU)
  println(expectedU)

  // PROGRAM:
  private val program: State[Int, (Int, Int, String, Int)] = for {
    _ <- set[Int](1)
    s1 <- get[Int]
    _ <- modify[Int](_ + 41)
    s2 <- get[Int]
    s3 <- inspect[Int, String](arg => s"`Hello: $arg`")
    s4 <- get[Int]
  } yield (s1, s2, s3, s4)

  println(s"Program: ${program.run(0).value._2.toString}")

}
