package controller

import akka.http.scaladsl.server.Directives._
import model.{Configuration, RatesResponse}
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import exception.RuntimeError
import service.RateFetchService

import scala.util.{Failure, Success}

class Routes(implicit val injector: Injector) extends Injectable {

  implicit val ec = inject[ExecutionContext]
  val configuration = inject[Configuration]
  val rateFetchService = inject[RateFetchService]

  def routes = path("rates") {
    get {
      parameters('currencies ? "") { (currencies) =>

        val liveRates: Future[RatesResponse] = rateFetchService.fetchRates(currencies = if(currencies.isEmpty) configuration.currencies else currencies.split(",").toList)
        onComplete(liveRates) {
          case  Success(liveRates : RatesResponse) => complete(liveRates)
          case  Failure(ex : RuntimeError) => complete(ex)
        }

      }
    }
  }

}
