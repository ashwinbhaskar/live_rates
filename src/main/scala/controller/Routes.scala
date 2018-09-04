package controller

import akka.http.scaladsl.server.Directives._
import client.LiveRateClient
import model.Configuration
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._

class Routes(implicit val injector: Injector) extends Injectable {

  implicit val ec = inject[ExecutionContext]
  val config = inject[Configuration]
  val liveRateClient = inject[LiveRateClient]

  def routes = path("rates") {
    get {
      parameters('baseCurrency ? "USD", 'currencies) { (baseCurrency, currencies) =>
        val liveRates: Future[Map[String, Double]] = liveRateClient.getRate(baseCurrency = baseCurrency, currencyList = currencies.split(",").toList)
        onSuccess(liveRates) {
          case k: Map[String, Double] => complete(k)
        }
      }
    }
  }

}
