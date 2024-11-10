package implicits

object LinearzationAndImplicits extends App {

  val f: MyFunction = new MyFunction
  print(f.apply(1))

  abstract class Show[A]() {
    def shows(a: A): String
  }

  implicit val IntShow = new Show[Int] {
    def shows(a: Int) = a.toString
  }

  implicit def ListShow[T] = new Show[List[T]] {
    def shows(a: List[T]) = a.mkString(", ")
  }

  trait ShowSyntax[A] {
    def shows: String
  }

  implicit def toShowSyntax[A: Show](a: A) = new ShowSyntax[A] {
    def shows = implicitly[Show[A]].shows(a)
  }

  println(5.shows)
  // prints '5‘
  import scala.language.postfixOps
  locally {
    implicit val AltIntShow = new Show[Int] {
      def shows(i: Int) = (1 to i).map(_ => "|").mkString
    }
    //    println(5.shows) // prints '|||||'
  }

  private[implicits] class MyFunction extends Function1[Int, Int] {
    def apply(arg: Int): Int = arg
  }


  private class Animal {
    def f() = "Animal"
  }

  private trait Furry extends Animal {
    override def f() = "Furry (super = " + super.f + ")"
  }

  private trait HasLegs extends Animal {
    override def f() = "HasLegs (super = " + super.f + ")"
  }

  private trait FourLegged extends HasLegs {
    override def f() = "FourLegged (super = " + super.f + ")"
  }

  private class Cat extends Animal with Furry with FourLegged {
    override def f() = "Cat (super = " + super.f + ")"
  }

}