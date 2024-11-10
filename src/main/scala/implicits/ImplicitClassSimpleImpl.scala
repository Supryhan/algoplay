package implicits

private[implicits] sealed trait Animal

private[implicits] case object Dog extends Animal

private[implicits] case object Bear extends Animal

private[implicits] case object Cow extends Animal

private[implicits] case class Habitat[A <: Animal](name: String)

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
