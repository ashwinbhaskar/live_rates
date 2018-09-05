package client

import Alias.{Currency, Rates}

import scala.concurrent.Future


trait LiveRateClient {

  def getRate(baseCurrency : Currency, currencyList : List[Currency]) : Future[Rates]

}
