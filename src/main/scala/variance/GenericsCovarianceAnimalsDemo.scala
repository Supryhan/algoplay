package variance

case class Named[+T](name: String, get: T)

object GenericsCovarianceAnimalsDemo extends App {
  val g: GenericParent = new GenericParent
  val c: Cat = Cat(14, "ham")
  val namedCat: Named[Cat] = Named[Cat]("", Cat(12, ""))
  g.speak(namedCat)
  var tmp = Named("doggy", Dog(12))
  g.speak(tmp)
  g.speak(Named("doggy", Dog(12)))
  g.speak(Named("kitty", c))
  g.speak(Named("shready", ShroedingersCat))
}

class GenericParent {
  def speak(namedAnimal: Named[Animal]): Unit = {
    val animal = namedAnimal.get
    if (animal.isAlive)
      println(s"${namedAnimal.name}: ${animal.tongue}")
  }
}
