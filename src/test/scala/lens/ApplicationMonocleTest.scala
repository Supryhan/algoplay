package lens

import lens.ApplicationMonocle.Manual
import org.scalatest.FunSuite

class ApplicationMonocleTest extends MonocleSuite {
  val initialPerson = Person("Mike", Address(1333, Street("KarlsPlaz")))
  test("_)") {
//    Manual.personManualLens.get(initialPerson) shouldEqual initialPerson
  }
}
