package lens

import lens.ApplicationMonocle.Manual
import org.scalatest.FunSuite
import utils.MonocleSuite

class ApplicationMonocleTest extends MonocleSuite {
  val expectedPerson = Person("Mike", Address(1333, Street("KarlsPlaz")))
  val expectedAddress = Address(1333, Street("KarlsPlaz"))
  test("test manual personToAddress lens ") {
    Manual.personManualLens.get(expectedPerson) shouldEqual expectedAddress
  }
}
