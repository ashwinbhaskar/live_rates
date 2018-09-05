package service

import Alias.Currency
import model.RatesResponse

import scala.concurrent.Future

trait RateFetchService {
  def fetchRates(currencies : List[Currency]) : Future[RatesResponse]
}
