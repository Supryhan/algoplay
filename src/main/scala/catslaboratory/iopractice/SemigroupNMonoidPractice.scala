package catslaboratory.iopractice

import cats.Monoid

object SemigroupNMonoidPractice extends App {
  case class ShoppingCard(items: List[String], total: BigDecimal)

//  implicit object ShoppingCardMonoid extends Monoid[ShoppingCard] {
//    override def empty: ShoppingCard = ShoppingCard(List.empty[String], BigDecimal(0))
//
//    override def combine(x: ShoppingCard, y: ShoppingCard): ShoppingCard = ShoppingCard(y.items ::: x.items, y.total + x.total)
//  }

  implicit val shoppingCardMonoid: Monoid[ShoppingCard] = Monoid.instance[ShoppingCard] (
    ShoppingCard(List.empty[String], BigDecimal(0)),
    (x: ShoppingCard, y: ShoppingCard) => ShoppingCard(y.items ::: x.items, y.total + x.total)
  )

  def checkout(cards: List[ShoppingCard])(implicit sc: Monoid[ShoppingCard]): Double = cards.foldLeft(sc.empty)(sc.combine).total.doubleValue

  println(
    s"""
       |My shoppings:
       |${checkout(List(ShoppingCard(List("1", "2", "3"), 123), ShoppingCard(List("4", "5", "6"), 456)))}
       |""".stripMargin)

}
