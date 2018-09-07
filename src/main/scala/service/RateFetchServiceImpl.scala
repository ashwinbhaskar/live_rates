package service
import java.util.Calendar

import Alias.{Currency, Rates}
import com.typesafe.config.Config
import exception.NoRatesInSystem
import model.RatesResponse
import scaldi.{Injectable, Injector}
import wrapper.RedisClientWrapper

import scala.concurrent.{ExecutionContext, Future}

class RateFetchServiceImpl(implicit val injector : Injector) extends RateFetchService with Injectable {

  val rediClientWrapper = inject[RedisClientWrapper]
  implicit val ec = inject[ExecutionContext]
  val currencyConfigService = inject[CurrencyConfigurationService]
  val baseCurrency = inject[Config].getString("configuration.qa.baseCurrency")


  override def fetchRates(currencies: List[Currency]): Future[RatesResponse] = {
    validate(currencies).flatMap(_ =>rediClientWrapper.getRates(currencies)
      .flatMap(rates => if(rates.isEmpty) {
        throw new NoRatesInSystem()
      }
    else {
      rediClientWrapper.getLastUpdatedTimeStamp.map(timestamp =>
        RatesResponse(timeStamp = timestamp
          , baseCurrency = baseCurrency
          , rates = rates))
    }
    ))
  }

  private def validate(list : List[Currency]) : Future[Unit] = {
    currencyConfigService.validateCurrencies(list)
  }
}
