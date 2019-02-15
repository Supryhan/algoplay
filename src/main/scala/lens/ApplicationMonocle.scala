package lens

import monocle.Lens
import monocle.macros.{GenLens, Lenses}

import scala.annotation.compileTimeOnly

@compileTimeOnly("enable macro paradise to expand macro annotations")
object ApplicationMonocle extends App {

  @Lenses("lensTo_") case class Street(name: String)
  @Lenses("lensTo_") case class Address(number: Int, street: Street)
  @Lenses("lensTo_") case class Person(name: String, address: Address)

  object Manual {
    val streetManualLens =
      Lens[Street, String](_.name)(string =>
        street => street.copy(name = string))
    val addressManualLens = Lens[Address, Street](_.street)(street =>
      address => address.copy(street = street))
    val personManualLens = Lens[Person, Address](_.address)(address =>
      person => person.copy(address = address))
    val changeStreetNameManualLens = personManualLens ^|-> addressManualLens ^|-> streetManualLens
  }
  object Semi {
    val streetSemiLens: Lens[Street, String] = GenLens[Street](_.name)
    val addressSemiLens: Lens[Address, Street] = GenLens[Address](_.street)
    val personSemiLens: Lens[Person, Address] = GenLens[Person](_.address)
    val changeStreetNameSemiLens = personSemiLens ^|-> addressSemiLens ^|-> streetSemiLens
  }
  object Macro {
    val streetMacroLens: Lens[Street, String] = Street.lensTo_name
    val addressMacroLens: Lens[Address, Street] = Address.lensTo_street
    val personMacroLens: Lens[Person, Address] = Person.lensTo_address
    val changeStreetNameMacroLens = personMacroLens ^|-> addressMacroLens ^|-> streetMacroLens
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
  val personManualLensCopy =
    changeStreetNameManualLens.set("Leopoldstrasse")(initialPerson)
  import Semi._
  val personSemiLensCopy =
    changeStreetNameSemiLens.set("Mariastrasse")(initialPerson)
}
