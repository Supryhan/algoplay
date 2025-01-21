package catslaboratory.monadslab

object StateMonadPractice extends App {

  case class ShoppingCart(items: List[String], total: Double)

  import cats.data.State

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

}
