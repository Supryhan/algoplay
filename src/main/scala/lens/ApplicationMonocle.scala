package lens

import monocle.Lens
import monocle.macros.GenLens

object ApplicationMonocle extends App {
  val initialPerson = Person("Mike", Address(1333, Street("KarlsPlaz")))
  val personDirectCopy = initialPerson.copy(
    address = initialPerson.address.copy(
      street = initialPerson.address.street.copy(
        name = "Sechshauserstrasse"
      )
    )
  )
  import Manual._
  lazy val personManualLensCopy =
    changeStreetNameManualLens.set("Leopoldstrasse")(initialPerson)
  import Semi._
  lazy val personSemiLensCopy =
    changeStreetNameSemiLens.set("Mariastrasse")(initialPerson)

  val changeStreetNameManualLens = personManualLens composeLens addressManualLens composeLens streetManualLens
  val changeStreetNameSemiLens = personSemiLens composeLens addressSemiLens composeLens streetSemiLens

  object Manual {
    val streetManualLens = Lens[Street, String](_.name)(string =>
      street => street.copy(name = string))
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

  println(initialPerson)
  println(personDirectCopy)
  println(personManualLensCopy)
  println(personSemiLensCopy)
}

case class Street(name: String)
case class Address(number: Int, street: Street)
case class Person(name: String, address: Address)
