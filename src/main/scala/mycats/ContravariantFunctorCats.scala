package mycats

object ContravariantFunctorCats extends App {


  import cats.Contravariant
  import cats.Show
  import cats.instances.string._

  val showString: Show[String] = Show[String]
  val showSymbol: Show[Symbol] = Contravariant[Show].contramap(showString)((sym: Symbol) => s"'${sym.name}")
  println(showSymbol.show('dave))
}

trait Contravariant[F[_]] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
}