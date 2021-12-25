package catslaboratory

object ContravariantFunctorCats extends App {


  import cats.Contravariant
  import cats.Show
  import cats.instances.string._

  val showString: Show[String] = Show[String]
  val showSymbol: Show[Symbol] = Contravariant[Show]
    .contramap[String, Symbol](showString)((sym: Symbol) => s"'${sym.name}")

  println(showSymbol.show('dave))

  import cats.syntax.contravariant._ // for contramap
  println(showString.contramap[Symbol](_.name).show('dave))
}

trait Contravariant[F[_]] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
}