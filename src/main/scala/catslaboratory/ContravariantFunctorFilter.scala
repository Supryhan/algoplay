package catslaboratory

import cats.Contravariant

trait Filter[T] {
  def filter(value: T): Boolean
}

case class SmallPack(value: String)
case class Pack(value: SmallPack)
case class LargePack(value: Pack)

object Filter {

  def apply[T](implicit instance: Filter[T]): Filter[T] = instance

  def filter[A](v: A)(implicit instance: Filter[A]): Boolean = instance.filter(v)

  val simpleFilterFunctor: Contravariant[Filter] = new Contravariant[Filter] {
    override def contramap[A, B](fa: Filter[A])(f: B => A): Filter[B] = (value: B) => fa.filter(f(value))
  }

  implicit val stringFilter: Filter[String] = (value: String) => value.length > 13
  implicit val intFilter: Filter[Int] = (value: Int) => value == 42

  implicit val filterSmallPack: Filter[SmallPack] = simpleFilterFunctor.contramap[String, SmallPack](Filter[String])((sb: SmallPack) => sb.value)
  implicit val filterPack: Filter[Pack] = simpleFilterFunctor.contramap[SmallPack, Pack](filterSmallPack)((b: Pack) => b.value)
  implicit val filterLargePack: Filter[LargePack] = simpleFilterFunctor.contramap[Pack, LargePack](filterPack)((lb: LargePack) => lb.value)

}

object ContravariantFunctorFilter extends App {

  val largePack: LargePack = LargePack(Pack(SmallPack("very long pack results in true")))

  println(s"Result: ${Filter.filter(largePack)}")

}
