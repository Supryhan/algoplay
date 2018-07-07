package implicits

sealed trait Animal

case object Dog extends Animal

case object Bear extends Animal

case object Cow extends Animal

case class Habitat[A <: Animal](name: String)

object ImplicitClassSimpleImpl extends App {

  implicit val dogHabitat = Habitat[Dog.type]("House")
  implicit val bearHabitat = Habitat[Bear.type]("Forest")

  implicit class AnimalOps[A <: Animal](animal: A) {
    def getHabitat(implicit habitat: Habitat[A]): Habitat[A] = habitat
  }

  println(Dog.getHabitat)
  println(Bear.getHabitat)
  //will not compile:
  //println(Cow.getHabitat)
}
