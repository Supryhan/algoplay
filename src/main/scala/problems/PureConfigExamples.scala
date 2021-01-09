package problems

import pureconfig.ConfigReader.Result
import pureconfig._
import pureconfig.generic.auto._

object PureConfigExamples extends App {
  val result: Result[ServiceConf] = ConfigSource.default.load[ServiceConf]
  val res: Object = result.getOrElse(null)
  println(s"Config: $res")
}

case class Port(number: Int) extends AnyVal

sealed trait AuthMethod
case class Login(username: String, password: String) extends AuthMethod
case class Token(token: String) extends AuthMethod
case class PrivateKey(pkFile: java.io.File) extends AuthMethod

case class ServiceConf(
                        host: String,
                        port: Port,
                        useHttps: Boolean,
                        authMethods: List[AuthMethod]
                      )