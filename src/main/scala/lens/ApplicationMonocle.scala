package lens

import monocle.Lens
import monocle.macros.{GenLens, Lenses}

import scala.annotation.compileTimeOnly

@compileTimeOnly("enable macro paradise to expand macro annotations")
object ApplicationMonocle extends App {

  @Lenses case class Street(name: String)
  @Lenses case class Address(number: Int, street: Street)
  @Lenses case class Person(name: String, address: Address)

  object Manual {
    val streetManualLens =
      Lens[Street, String](_.name)(string => street => street.copy(name = string))
    val addressManualLens = Lens[Address, Street](_.street)(street =>
      address => address.copy(street = street))
    val personManualLens = Lens[Person, Address](_.address)(address =>
      person => person.copy(address = address))
  }
  object Semi {
    val streetSemiLens: Lens[Street, String] = GenLens[Street](_.name)
    val addressSemiLens: Lens[Address, Street] = GenLens[Address](_.street)
    val personSemiLens: Lens[Person, Address] = GenLens[Person](_.address)
  }
  object Macro {
    //      val streetToNameMacroLens: Lens[Street, String] = Street.name
    //    val addressToStreetMacroLens: Lens[Address, Street] = Address.street
    //    val personToAddressMacroLens: Lens[Person, Address] = Person.address
  }
  val initialPerson = Person("Mike", Address(1333, Street("KarlsPlaz")))
  val personDirectCopy = initialPerson.copy(
    address = initialPerson.address.copy(
      street = initialPerson.address.street.copy(
        name = "Sechshauserstrasse"
      )
    )
  )
  import Manual._
  val changeStreetNameManualLens = personManualLens ^|-> addressManualLens ^|-> streetManualLens
  val personManualLensCopy =
    changeStreetNameManualLens.set("Leopoldstrasse")(initialPerson)
  import Semi._
  val changeStreetNameSemiLens = personSemiLens ^|-> addressSemiLens ^|-> streetSemiLens
  val personSemiLensCopy =
    changeStreetNameSemiLens.set("Mariastrasse")(initialPerson)

  println(initialPerson)
  println(personDirectCopy)
  println(personManualLensCopy)
  println(personSemiLensCopy)
  println(Street.name.get(Street("KarlsPlaz")))
  println(Person.address.get(initialPerson))
}
