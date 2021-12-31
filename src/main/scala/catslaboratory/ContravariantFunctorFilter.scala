package catslaboratory

import cats.Contravariant

trait Filter[T] {
  def filter(value: T): Boolean
}

object Filter {

  def apply[T](implicit instance: Filter[T]): Filter[T] = instance

  def filter[A](v: A)(implicit flt: Filter[A]): Boolean = flt.filter(v)

  implicit object StringFilter extends Filter[String] {
    override def filter(value: String): Boolean = value.length > 13
  }

  implicit object IntFilter extends Filter[Int] {
    override def filter(value: Int): Boolean = value == 42
  }

  val simpleFilterFunctor: Contravariant[Filter] = new Contravariant[Filter] {
    override def contramap[A, B](fa: Filter[A])(f: B => A): Filter[B] = new Filter[B] {
      override def filter(value: B): Boolean = fa.filter(f(value))
    }
  }
}

object ContravariantFunctorFilter extends App {

  import Filter.simpleFilterFunctor

  case class SmallBox(value: String)
  case class Box(value: SmallBox)
  case class LargeBox(value: Box)

  val filterString: Filter[String] = Filter[String]

  val filterSmallBox: Filter[SmallBox] = simpleFilterFunctor.contramap[String, SmallBox](filterString)((sb: SmallBox) => sb.value)
  val filterBox: Filter[Box] = simpleFilterFunctor.contramap[SmallBox, Box](filterSmallBox)((b: Box) => b.value)
  val filterLargeBox: Filter[LargeBox] = simpleFilterFunctor.contramap[Box, LargeBox](filterBox)((lb: LargeBox) => lb.value)

  println(s"Result: ${filterLargeBox.filter(LargeBox(Box(SmallBox("very long box results in true"))))}")
}
