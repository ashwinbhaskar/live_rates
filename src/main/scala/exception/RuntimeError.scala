package exception

import spray.json.DefaultJsonProtocol

case class RuntimeError(errorCode : Int, message : String ) extends RuntimeException

object RuntimeError extends DefaultJsonProtocol{
  implicit val formatter = jsonFormat(RuntimeError.apply _, "errorCode","ErrorMessage")
}