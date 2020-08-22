package implicits

object ExtendAnyVal extends App {

  implicit class DoubleOps(val double: Double) extends AnyVal {
    def isZero: Boolean = double == 0
  }

  print(3.0.isZero)
}