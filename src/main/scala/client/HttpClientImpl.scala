package client
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.{Unmarshal, Unmarshaller}
import akka.stream.ActorMaterializer
import scaldi.{Injectable, Injector}
import spray.json._

import scala.concurrent.{ExecutionContext, Future}

class HttpClientImpl(implicit injector : Injector) extends HttpClient with Injectable {
  implicit val system = inject[ActorSystem]
  implicit val fm = inject[ActorMaterializer]
  implicit val ec = inject[ExecutionContext]
  override def execute[T](httpRequest: HttpRequest)(implicit unmarshaller: Unmarshaller[HttpResponse, T]): Future[T] = {
    Http().singleRequest(httpRequest).flatMap(httpResponse => Unmarshal(httpResponse).to[T])
  }
}
