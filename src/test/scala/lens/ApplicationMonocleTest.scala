package lens

import lens.ApplicationMonocle.{Address, Manual, Person, Street}
import lens.ApplicationMonocle.Manual.personManualLens
import lens.ApplicationMonocle.Semi.personSemiLens
import utils.MonocleSuite

import scala.annotation.compileTimeOnly

@compileTimeOnly("enable macro paradise to expand macro annotations")
class ApplicationMonocleTest extends MonocleSuite {

  test(
    "Manual Lens. " +
      "Address should be set into person and then returns updated address") {
    val person = Person("Mike", Address(1, Street("KarlsPlaz1")))
    val address = Address(1333, Street("KarlsPlaz2"))
    monocle.law.LensLaws(personManualLens).setGet(person, address)
    personManualLens.get(personManualLens.set(address)(person)) shouldEqual address
  }

  test("Manual Lens. " +
    "Address should be get from person and then set into person and then returns updated person") {
    val person = Person("Mike", Address(1333, Street("KarlsPlaz")))
    monocle.law.LensLaws(personManualLens).getSet(person)
    personManualLens.set(personManualLens.get(person))(person) shouldEqual person
  }

  test(
    "Semi Lens. " +
      "Address should be set into person and then returns updated address") {
    val person = Person("Mike", Address(1, Street("KarlsPlaz1")))
    val address = Address(1333, Street("KarlsPlaz2"))
    monocle.law.LensLaws(personSemiLens).setGet(person, address)
    personSemiLens.get(personSemiLens.set(address)(person)) shouldEqual address
  }

  test("Semi Lens. " +
    "Address should be get from person and then set into person and then returns updated person") {
    val person = Person("Mike", Address(1333, Street("KarlsPlaz")))
    monocle.law.LensLaws(personSemiLens).getSet(person)
    personSemiLens.set(personSemiLens.get(person))(person) shouldEqual person
  }
}
