package implicits

object FBoundedPolymorphism extends App {

  sealed trait Animal[A <: Animal[A]] {
    self: A =>
    def getHabitat(implicit habitat: Habitat[A]): Habitat[A] = habitat
  }

  trait Dog extends Animal[Dog]

  trait Bear extends Animal[Bear]

  trait Cow extends Animal[Cow]

  case object Dog extends Dog

  case object Bear extends Bear

  case object Cow extends Cow

  case class Habitat[A <: Animal[A]](name: String)

  implicit val dogHabitat = Habitat[Dog]("House")
  implicit val bearHabitat = Habitat[Bear]("Forest")

  println(Dog.getHabitat)
  println(Bear.getHabitat)
}

