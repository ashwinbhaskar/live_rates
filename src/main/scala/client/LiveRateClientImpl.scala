package client

import Alias.Currency
import akka.actor.{ActorRefFactory, ActorSystem}
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, RequestEntity, Uri}
import com.typesafe.config.Config
import scaldi.{Injectable, Injector}
import spray.json._
import unmarshaller.CustomHttpResponseUnmarshaller

import scala.concurrent.{ExecutionContext, Future}

class LiveRateClientImpl(implicit inj : Injector) extends LiveRateClient with Injectable with DefaultJsonProtocol {
  val liveRatesUrl = inject[Config].getString("configuration.qa.liveRatesUrl")
  val BASE_CURRENCY_KEY = "fsym"
  val TO_CURRENCY_KEY = "tsyms"
  val httpClient = inject[HttpClient]
  implicit val system = inject[ActorSystem]
  implicit val ec = inject[ExecutionContext]
  implicit val actorRef = inject[ActorRefFactory]

  override def getRate(baseCurrency: Currency, currencyList: List[Currency]): Future[Map[String, Double]] = {
    val liveRatesUri = Uri(liveRatesUrl).withQuery(Query((BASE_CURRENCY_KEY,baseCurrency),(TO_CURRENCY_KEY,currencyList.mkString(","))))
    val httpRequest = HttpRequest(method = HttpMethods.GET,uri = liveRatesUri)
    httpClient.execute[Map[String,Double]](httpRequest)(CustomHttpResponseUnmarshaller.httpResponseUnmarshaller)
  }
}
