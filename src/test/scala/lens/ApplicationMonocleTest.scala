package lens

import lens.ApplicationMonocle._
import utils.MonocleSuite

import scala.annotation.compileTimeOnly

@compileTimeOnly("enable macro paradise to expand macro annotations")
class ApplicationMonocleTest extends MonocleSuite {

  test(
    "Manual Lens. " +
      "Street name should be set into person and then returns updated address and vice versa") {
    val person = Person("Mike", Address(1, Street("KarlsPlaz1")))
    val street = "KarlsPlaz2"
    monocle.law.LensLaws(Manual.changeStreetNameManualLens).setGet(person, street)
    monocle.law.LensLaws(Manual.changeStreetNameManualLens).getSet(person)
  }

  test(
    "Semi Lens. " +
      "Street name should be set into person and then returns updated address and vice versa") {
    val person = Person("Mike", Address(1, Street("KarlsPlaz1")))
    val street = "KarlsPlaz2"
    monocle.law.LensLaws(Semi.changeStreetNameSemiLens).setGet(person, street)
    monocle.law.LensLaws(Semi.changeStreetNameSemiLens).getSet(person)
  }

  test("Macro Lens. " +
    "Street name should be set into person and then returns updated address and vice versa") {
    val person = Person("Mike", Address(1, Street("KarlsPlaz1")))
    val street = "KarlsPlaz2"
    monocle.law.LensLaws(Macro.changeStreetNameMacroLens).setGet(person, street)
    monocle.law.LensLaws(Macro.changeStreetNameMacroLens).getSet(person)
  }
}
