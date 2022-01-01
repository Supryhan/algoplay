package catslaboratory

import cats.implicits.catsSyntaxOptionId
import cats.Contravariant
import catslaboratory.Filter.filterLargePack
import monocle.Lens
import monocle.macros.Lenses

trait Filter[T] {
  def filter(value: T): Boolean
}

@Lenses("lensTo_") case class SmallPack(value: String)
@Lenses("lensTo_") case class Pack(value: SmallPack)
@Lenses("lensTo_") case class LargePack(value: Pack)

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
  implicit val monoidLargePack: Monoid[LargePack] = new Monoid[LargePack] {
    def empty: LargePack = LargePack(Pack(SmallPack("")))
    def combine(a1: LargePack, a2: LargePack): LargePack = LargePack(Pack(SmallPack(s"${a1.value.value.value}::${a2.value.value.value}")))
  }

  implicit def filterOption[T](implicit flt: Filter[T], m: Monoid[T]): Filter[Option[T]] =
    simpleFilterFunctor.contramap[T, Option[T]](flt)((value: Option[T]) => value.fold(m.empty)(identity))

}

object Macro {
  val smallPackLens: Lens[SmallPack, String] = SmallPack.lensTo_value
  val packLens: Lens[Pack, SmallPack] = Pack.lensTo_value
  val largePackLens: Lens[LargePack, Pack] = LargePack.lensTo_value
  val changeSmallPackValue: Lens[LargePack, String] = largePackLens ^|-> packLens ^|-> smallPackLens
}

object ContravariantFunctorFilter extends App {

  import Macro._
  val largePack: LargePack = LargePack(Pack(SmallPack("very long pack results in true")))
  val largePackOp: Option[LargePack] = changeSmallPackValue.set("short text")(largePack).some

  println(s"Result for Pack: ${Filter.filter(largePack)}") // true
  import Filter.monoidLargePack // import implicit Monoid[LargePack]
  println(s"Result for Option: ${Filter.filter(largePackOp)}") // false

}
