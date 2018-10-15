package compositions

object SimpleComposition {

  case class Wheat()

  case class Flour()

  case class Dough()

  case class Bread()

  def grind: (Wheat => Flour) = w => {
    println("make the flour");
    Flour()
  }

  def kneadDough: (Flour => Dough) = f => {
    println("make the dough");
    Dough()
  }

  def distributeDough: (Dough => Seq[Dough]) = d => {
    println("distribute the dough");
    Seq[Dough]()
  }

  def bake: (Int => Int => Seq[Dough] => Seq[Bread]) =
    temperature => duration => sd => {
      println(s"bake the bread, duration: $duration, temperature: $temperature")
      Seq[Bread]()
    }

  def bakeRecipe1 = bake(350)(45)

  def main(args: Array[String]): Unit = {
    (grind >> kneadDough >> distributeDough >> bakeRecipe1) (Wheat())
  }

  implicit class Forward[A, B](f: A => B) {
    def >>[C](g: B => C): A => C = source => g(f(source))
  }

}
