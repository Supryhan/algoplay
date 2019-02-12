package lens

import lens.ApplicationMonocle.Manual
import utils.MonocleSuite
import Manual._

class ApplicationMonocleTest extends MonocleSuite {

  test("test manual personToAddress lens ") {
    val person = Person("Mike", Address(1333, Street("KarlsPlaz")))
    val address = Address(1333, Street("KarlsPlaz"))
    personManualLens.get(person) shouldEqual address
  }

  test("address should be set into person and then returns the updated address") {
    val person = Person("Mike", Address(1, Street("KarlsPlaz1")))
    val address = Address(1333, Street("KarlsPlaz2"))
    monocle.law.LensLaws(personManualLens).setGet(person, address)
    personManualLens.get(personManualLens.set(address)(person)) shouldEqual address
  }

  test("address should be get from person and then returns") {
    val person = Person("Mike", Address(1333, Street("KarlsPlaz")))
    monocle.law.LensLaws(personManualLens).getSet(person)
    personManualLens.set(personManualLens.get(person))(person) shouldEqual person
  }
}
