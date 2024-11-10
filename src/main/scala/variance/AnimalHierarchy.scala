package variance


private[variance] abstract class Animal(val tongue: String) {
  val age: Int
  def isAlive: Boolean
}

private[variance] case class Dog(age: Int) extends Animal("bark") {
  def isAlive = age < 13
}

private[variance] case class Cat(age: Int, diet: String) extends Animal("meow") {
  def isAlive = age < 15
}

private[variance] object ShroedingersCat extends Cat(0, "neutrino") {
  val rng = new scala.util.Random()
  override def isAlive = rng.nextDouble() < 0.5
}

private[variance] object Werwolf extends Dog(100500) {
  //(name: String = "lycanthrope")
  val rng = new scala.util.Random()
  override def isAlive = rng.nextDouble() < 0.5
}