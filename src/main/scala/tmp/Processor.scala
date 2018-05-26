package tmp

class Origin
class Parent extends Origin
class Child extends Parent

class Invariant[A](val l: A)
class Covariant[+A](val l: A)
class Contravariant[-A]()

class Processor[A] {
  def process(inv: Invariant[A]): Invariant[A] = inv
  def process(cov: Covariant[A]): Covariant[A] = cov
  def process(cont: Contravariant[A]): Contravariant[A] = cont
}

class ProcessorInheritor[A] {
  def process[A <: Parent](c: Invariant[A]): Invariant[A] = c
}

class ProcessorSuper[A] {
  def process[A >: Parent](c: Invariant[A]): Invariant[A] = c
}

object Processor extends App {
  val inv1 = new Processor[Parent].process(new Invariant[Parent](new Child))
  val inv2 = new Processor[Parent].process(new Invariant[Parent](new Child))
  //  val inv3 = new Processor[Parent].process(new Invariant[Child](new Child))
  //  val inv4 = new Processor[Parent].process(new Invariant[Origin](new Child))
  new Processor[Parent].process(new Covariant[Parent](new Child))
  new Processor[Parent].process(new Covariant[Child](new Child))
  val ct1 = new Processor[Parent].process(new Contravariant[Origin]())
  val ct2 = new Processor[Parent].process(new Contravariant[Parent]())
  //  val ct3 = new Processor[Parent].process(new Contravariant[Child]())

//  new ProcessorInheritor[Origin].process(new Invariant[Origin](new Child))
  new ProcessorInheritor[Parent].process(new Invariant[Child](new Child))
  new ProcessorInheritor[Parent].process(new Invariant[Parent](new Child))
  new ProcessorInheritor[Child].process(new Invariant[Child](new Child))

//  new ProcessorSuper[Child].process(new Invariant[Child](new Child))
  new ProcessorSuper[Parent].process(new Invariant[Origin](new Child))
  new ProcessorSuper[Parent].process(new Invariant[Parent](new Child))
  new ProcessorSuper[Origin].process(new Invariant[Parent](new Child))
}