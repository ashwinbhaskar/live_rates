package exception

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsNumber, JsObject, JsString, JsValue, RootJsonWriter}
import spray.json._

object ServiceError extends DefaultJsonProtocol with SprayJsonSupport{
  implicit val jsonWriter = new JsonWriter[ServiceError] {
    override def write(obj: ServiceError): JsValue = {
      JsObject(
        "code" -> JsNumber(obj.errorCode),
        "message" -> JsString(obj.message))
    }
  }
}

case class ServiceError(errorCode : Int, message : String ) extends Exception{
  def toJsonString = this.toJson.toString
}
