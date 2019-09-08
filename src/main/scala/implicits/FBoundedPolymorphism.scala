package implicits

sealed trait RootOps[A <: RootOps[A]] {
  self: A =>
  def getSymbol(implicit symbol: Symbol[A]): Symbol[A] = symbol
}

trait Alpha extends RootOps[Alpha]

trait Beta extends RootOps[Beta]

trait Gamma extends RootOps[Gamma]

case object Alpha extends Alpha

case object Beta extends Beta

case object Gamma extends Gamma

case class Symbol[A <: RootOps[A]](name: String)

object FBoundedPolymorphism extends App {

  implicit val alfaSymbol = Symbol[Alpha](name = "alpha")
  implicit val betaSymbol = Symbol[Beta](name = "beta")
  implicit val gammaSymbol = Symbol[Gamma](name = "gamma")

  println(Alpha.getSymbol)
  println(Beta.getSymbol)
  println(Gamma.getSymbol)
}

