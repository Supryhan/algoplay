package problems

import org.scalatest.funsuite.AnyFunSuite
import pureconfig.ConfigReader.Result
import pureconfig.ConfigSource
import pureconfig.generic.auto._

import java.io.File

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

  test("test config has correct host") {
    val actual: Option[ServiceConf] = getConf(source)
    val expectedHost = ServiceConf(
      "test-example.com",
      Port(8080),
      false,
      1331,
      List(PrivateKey(new File("/home/test-user/myauthkey")), Login("testpureconfig", "12345678"))
    )
    assert(actual.map(x => x.host).getOrElse("error") == expectedHost.host)
  }
}
