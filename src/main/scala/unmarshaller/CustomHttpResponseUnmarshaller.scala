package unmarshaller

import Alias.Rates
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.unmarshalling.{FromResponseUnmarshaller, Unmarshal, Unmarshaller}
import akka.util.ByteString
import spray.json.JsonParser

import scala.concurrent.ExecutionContext

object CustomHttpResponseUnmarshaller {

  implicit def httpResponseUnmarshaller(implicit ec: ExecutionContext): FromResponseUnmarshaller[Rates] = {
    Unmarshaller.withMaterializer(_ => implicit mat => {
      case resp: HttpResponse if resp.status.isSuccess() =>
        resp.entity.dataBytes.runFold(ByteString.empty)(_ ++ _).map(_.utf8String).map{
          str =>
            JsonParser(str).asJsObject.fields.toList.map(f => (f._1,f._2.toString().toDouble)).toMap
        }

    })
  }
}