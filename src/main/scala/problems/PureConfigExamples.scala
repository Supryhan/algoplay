package problems

import pureconfig.ConfigReader.Result
import pureconfig._
import pureconfig.generic.auto._

import java.util.UUID

object PureConfigExamples extends App {
  val source: Result[ServiceConf] = ConfigSource.default.load[ServiceConf]
//  val source: Result[ServiceConf] = ConfigSource.defaultApplication.load[ServiceConf]
  println(s"Config: ${getConf(source).toString}")

  def getConf(source: Result[ServiceConf]): Option[ServiceConf] = source match {
    case Right(value) => Some(value)
    case Left(_) => None
  }

}

case class Port(number: Int) extends AnyVal

sealed trait AuthMethod
case class Login(username: String, password: String) extends AuthMethod
case class Token(token: UUID) extends AuthMethod
case class PrivateKey(pkFile: java.io.File) extends AuthMethod

case class ServiceConf(
                        host: String,
                        port: Port,
                        useHttps: Boolean,
                        authMethods: List[AuthMethod]
                      )