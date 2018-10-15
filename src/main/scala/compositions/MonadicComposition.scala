package compositions

object MonadicComposition {

  case class Wheat()

  case class Flour()

  case class Dough()

  case class Bread()

  def grind: Wheat => Either[Flour, String] = w => {
    println("make the flour")
    Left(Flour())
  }

  def kneadDough: Flour => Either[Dough, String] = f => {
    println("make the dough")
    Left(Dough())
  }

  def distributeDough: Dough => Either[Seq[Dough], String] = d => {
    println("distribute the dough")
    Left(Seq[Dough]())
  }

  def bake: Int => Int => Seq[Dough] => Either[Seq[Bread], String] =
    temperature => duration => sd => {
      println(s"bake the bread, duration: $duration, temperature: $temperature")
      Left(Seq[Bread]())
    }

  def bakeRecipe1 = bake(350)(45)

  def main(args: Array[String]): Unit = grind(Wheat()) >>= kneadDough >>= distributeDough >>= bakeRecipe1

  implicit class MonadicForward[Left, Right](twoTrackInput: Either[Left, Right]) {
    def >>=[Intermediate](switchFunction: Left => Either[Intermediate, Right]) =
      twoTrackInput match {
        case Left(s) => switchFunction(s)
        case Right(f) => Right(f)
      }
  }

}