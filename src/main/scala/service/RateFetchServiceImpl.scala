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
  val baseCurrency = inject[Config].getString("configuration.qa.baseCurrency")

  override def fetchRates(currencies: List[Currency]): Future[RatesResponse] = {

    val future = rediClientWrapper.getRates(currencies)
    future.map(rates => if(rates.isEmpty) throw new NoRatesInSystem(412, "There are no rates in the System")
    else
      RatesResponse(timeStamp = Calendar.getInstance().getTimeInMillis
        ,baseCurrency = baseCurrency
        ,rates = rates)
    )
  }
}
