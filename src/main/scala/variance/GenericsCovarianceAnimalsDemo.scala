package variance

case class Named[+T](name: String, get: T)

abstract class Animal(val tongue: String) {
  val age: Int
  def isAlive: Boolean
}

case class Dog(age: Int) extends Animal("bark") {
  def isAlive = age < 13
}

case class Cat(age: Int, diet: String) extends Animal("meow") {
  def isAlive = age < 15
}

object ShroedingersCat extends Cat(0, "neutrino") {
  val rng = new scala.util.Random()
  override def isAlive = rng.nextDouble() < 0.5
}

object GenericsCovarianceAnimalsDemo {
  def main(args: Array[String]) {
    val g: GenericParent = new GenericParent
    val c: Cat = new Cat(14, "ham")
    val namedCat: Named[Cat] = Named[Cat]("",Cat(12,""))
    g.speak(namedCat)
    var tmp = Named("doggy", new Dog(12))
    g.speak(tmp)
    g.speak(Named("doggy", new Dog(12)))
    g.speak(Named("kitty", c))
    g.speak(Named("shready", ShroedingersCat))
  }
}

class GenericParent {
  def speak(namedAnimal: Named[Animal]): Unit = {
    val animal = namedAnimal.get
    if (animal.isAlive)
      println(s"${namedAnimal.name}: ${animal.tongue}")
  }
}
