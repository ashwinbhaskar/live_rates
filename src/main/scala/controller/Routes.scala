package controller

import akka.http.scaladsl.server.Directives._
import model.{Configuration, RatesResponse}
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.MediaTypes.`application/json`
import exception.{NoRatesInSystem, ServiceError}
import service.RateFetchService

import scala.util.{Failure, Success}

class Routes(implicit val injector: Injector) extends Injectable {

  implicit val ec = inject[ExecutionContext]
  val configuration = inject[Configuration]
  val rateFetchService = inject[RateFetchService]

  def routes = path("rates") {
    get {
      parameters('currencies ? "") { (currencies) =>

        val sanitizedCurrencies = if(currencies.isEmpty) configuration.currencies else currencies.split(",").map(_.toUpperCase).toList
        val liveRates: Future[RatesResponse] = rateFetchService.fetchRates(currencies = sanitizedCurrencies)
        onComplete(liveRates) {
          case  Success(liveRates : RatesResponse) => complete(liveRates)
          case  Failure(error : ServiceError) =>  complete(error.errorCode, HttpEntity(`application/json`, error.toJsonString))
        }

      }
    }
  }

}
