package problems

import org.scalatest.funsuite.AnyFunSuite
import org.specs2.control.Properties.aProperty
import org.specs2.matcher.MustThrownMatchers.ok
import problems.PureConfigExamples.getConf
import pureconfig.ConfigReader.Result
import pureconfig.ConfigSource
import pureconfig.generic.auto._

class PureConfigExamplesTest extends AnyFunSuite {

  val source: Result[ServiceConf] = ConfigSource.default.load[ServiceConf]

  def getConf(source: Result[ServiceConf]): Option[ServiceConf] = source match {
    case Right(value) => Some(value)
    case Left(_) => None
  }

  test("test config is not empty") {
    assert(getConf(source) match {
      case Some(_) => true
      case None => false
    })
  }

  test("test config print") {
    val r = getConf(source)
    println(r match {
      case Some(value) => value.host
      case None => "none"
    })
    ok
  }
}
