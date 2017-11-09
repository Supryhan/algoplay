package tmp

class Origin
class Parent extends Origin
class Child extends Parent

class Container[+A](val l: A)
class ContainerInvariant[A](val l: A)

class ProcessorInvariant[A] {
  def process(c: Container[A]): Container[A] = c
}

class ProcessorInheritor[A] {
  def process[A <: Parent](c: ContainerInvariant[A]): ContainerInvariant[A] = c
}

class ProcessorSuper[A] {
  def process[A >: Parent](c: ContainerInvariant[A]): ContainerInvariant[A] = c
}

object ProcessorInvariant extends App {
  var processorInv = new ProcessorInvariant[Parent]
  var containerParentWithChild = new Container[Parent](new Child)
  var containerChildWithChild = new Container[Child](new Child)
  processorInv.process(containerParentWithChild)
  processorInv.process(containerChildWithChild)

  var processorInheritorOnlyAfterParent = new ProcessorInheritor[Origin] //error!!!
//  processorInheritorOnlyAfterParent.process(new ContainerInvariant[Origin](new Child))

  var processorInheritorAfterParent = new ProcessorInheritor[Parent]
  processorInheritorAfterParent.process(new ContainerInvariant[Child](new Child))

  var processorSuper = new ProcessorSuper[Origin]
  processorSuper.process(new ContainerInvariant[Origin](new Child))
  processorSuper.process(new ContainerInvariant[Parent](new Child))
//  processorSuperFromParent.process(new ContainerInvariant[Child](new Child))
}