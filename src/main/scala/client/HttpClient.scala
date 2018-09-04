package client


import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshaller

import scala.concurrent.Future

trait HttpClient {

  def execute[T](httpRequest : HttpRequest)(implicit unmarshaller : Unmarshaller[HttpResponse,T]) : Future[T]

}
