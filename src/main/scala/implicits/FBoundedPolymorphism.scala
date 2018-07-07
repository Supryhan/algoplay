package implicits

sealed trait RootOps[A <: RootOps[A]] {
  self: A =>
  def getSymbol(implicit symbol: Symbol[A]): Symbol[A] = symbol
}

trait Alfa extends RootOps[Alfa]

trait Beta extends RootOps[Beta]

trait Gamma extends RootOps[Gamma]

case object Alfa extends Alfa

case object Beta extends Beta

case object Gamma extends Gamma

case class Symbol[A <: RootOps[A]](name: String)

object FBoundedPolymorphism extends App {

  implicit val alfaSymbol = Symbol[Alfa]("alfa")
  implicit val betaSymbol = Symbol[Beta]("beta")
  implicit val gammaSymbol = Symbol[Gamma]("gamma")

  println(Alfa.getSymbol)
  println(Beta.getSymbol)
  println(Gamma.getSymbol)
}

