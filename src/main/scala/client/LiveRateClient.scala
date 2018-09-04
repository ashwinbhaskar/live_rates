package client

import Alias.Currency

import scala.concurrent.Future


trait LiveRateClient {

  def getRate(baseCurrency : Currency, currencyList : List[Currency]) : Future[Map[String,Double]]

}
