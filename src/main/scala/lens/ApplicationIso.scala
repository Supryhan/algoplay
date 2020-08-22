package lens

import monocle.Iso

object ApplicationIso extends App {

  case class Address(name: String)

  case class Person(name: String, age: Int, address: Address)

  val personIso: Iso[Person, Tuple3[String, Int, Address]] =
    Iso[Person, Tuple3[String, Int, Address]](p => (p.name, p.age, p.address)) { case Tuple3(n, a, aa) => Person(n, a, aa) }
  val addressIso: Iso[Address, Tuple1[String]] =
    Iso[Address, Tuple1[String]](a => Tuple1(a.name)) { case Tuple1(a) => Address(a) }
  //    val personIsoUpperCasePlusOne: Iso[Person, Tuple2[String, Int]] = personIso.modify(_._1.toUpperCase)
  //  val personAddressIso =  addressIso composeIso personIso
}
