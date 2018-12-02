package variance

class Origin
class Parent extends Origin
class Child extends Parent

class Inv[A](val l: A)
class Cov[+A](val l: A)
class Contr[-A]()

class Processor[A] {
  def process(inv: Inv[A]): Inv[A] = inv
  def process(cov: Cov[A]): Cov[A] = cov
  def process(cont: Contr[A]): Contr[A] = cont
}

class ProcessorInheritor[A] {
  def process[A <: Parent](c: Inv[A]): Inv[A] = c
}

class ProcessorSuper[A] {
  def process[A >: Parent](c: Inv[A]): Inv[A] = c
}

object Processor extends App {
  val inv1 = new Processor[Parent].process(new Inv[Parent](new Child))
  val inv2 = new Processor[Parent].process(new Inv[Parent](new Child))
  val inv3 = new Processor[Parent].process(new Inv[Parent](new Parent))
  //  val err = new Processor[Parent].process(new Inv[Child](new Child))
  //  val err = new Processor[Parent].process(new Inv[Origin](new Child))
  new Processor[Parent].process(new Cov[Parent](new Child))
  new Processor[Parent].process(new Cov[Child](new Child))
  val ct1 = new Processor[Parent].process(new Contr[Origin]())
  val ct2 = new Processor[Parent].process(new Contr[Parent]())
  //  val err = new Processor[Parent].process(new Contr[Child]())

//  val err = new ProcessorInheritor[Origin].process(new Inv[Origin](new Child))
  new ProcessorInheritor[Parent].process(new Inv[Child](new Child))
  new ProcessorInheritor[Parent].process(new Inv[Parent](new Child))
  new ProcessorInheritor[Child].process(new Inv[Child](new Child))

//  val err = new ProcessorSuper[Child].process(new Inv[Child](new Child))
  new ProcessorSuper[Parent].process(new Inv[Origin](new Child))
  new ProcessorSuper[Parent].process(new Inv[Parent](new Child))
  new ProcessorSuper[Origin].process(new Inv[Parent](new Child))
}