package client

import Alias.Currency
import com.typesafe.config.Config
import scaldi.Injectable

import scala.concurrent.Future

class LiveRateClientImpl extends LiveRateClient with Injectable{

  val url = inject[Config].getString("configuration.qa.liveRatesUrl")

  override def getRate(baseCurrency: Currency, currencyList: List[Currency]): Future[Map[String, Double]] = {

  }
}
