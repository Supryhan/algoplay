package compositions

object MonadicComposition {

  case class Wheat(name: String)

  case class Flour()

  case class Dough()

  case class Bread()

  def grind: Wheat => Either[Flour, String] = w => {
    println("make the flour")
    if("Durum" == w.name)
      Right("Is not a proper type of wheat")
    else
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

  def main(args: Array[String]): Unit = grind(Wheat("Durum1")) >>= kneadDough >>= distributeDough >>= bakeRecipe1

  implicit class MonadicForward[Left, Right](twoTrackInput: Either[Left, Right]) {
    def >>=[Intermediate](switchFunction: Left => Either[Intermediate, Right]) =
      twoTrackInput match {
        case Left(s) => switchFunction(s)
        case Right(f) => { println("Broken sequence, root cause: " + f); Right(f) }
      }
  }

}