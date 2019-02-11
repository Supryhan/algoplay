package lens

import monocle.Lens
import monocle.macros.GenLens

object LensTrainer extends App {
  val initialPerson = Person("Mike", Address(1333, Street("KarlsPlaz")))
  val personCopy = initialPerson.copy(
    address = initialPerson.address.copy(
      street = initialPerson.address.street.copy(
        name = "Sechshauserstrasse"
      )
    )
  )

//  val streetLens = Lens[Street, String](_.name)(string => street => street.copy(name = string))
  val streetLens_ :Lens[Street, String] = GenLens[Street](_.name)
//  val addressLens = Lens[Address, Street](_.street)(street => address => address.copy(street = street))
  val addressLens_ :Lens[Address, Street] = GenLens[Address](_.street)
//  val personLens = Lens[Person, Address](_.address)(address => person => person.copy(address = address))
  val personLens_ :Lens[Person, Address] = GenLens[Person](_.address)

//  val changeStreetNameLens = personLens.composeLens(addressLens).composeLens(streetLens)
  val changeStreetNameLens = personLens_.composeLens(addressLens_).composeLens(streetLens_)

  val personLensCopy = changeStreetNameLens.set("Leopoldstrasse")(initialPerson)

  println(initialPerson)
  println(personCopy)
  println(personLensCopy)
}

case class Street(name: String)

case class Address(number: Int, street: Street)

case class Person(name: String, address: Address)
