package service

import java.nio.ByteBuffer

import Alias.{Currency, Rates}
import akka.event.jul.Logger
import client.{Constants, LiveRateClient}
import com.redis.RedisClient
import com.typesafe.config.Config
import model.Configuration
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Success
import scala.util.Failure

import org.slf4j.LoggerFactory
import wrapper.RedisClientWrapper


class RateUpdateService(implicit val inj : Injector) extends Injectable {

  val liveRateClient = inject[LiveRateClient]
  val redisClientWrapper = inject[RedisClientWrapper]
  val configuration = inject[Configuration]
  val config = inject[Config]
  val logger = LoggerFactory.getLogger(this.getClass)
  implicit val ec = inject[ExecutionContext]

  def updateLiveRate() : Unit = {
    val future :Future[Rates]= liveRateClient.getRate(baseCurrency = config.getString("configuration.qa.baseCurrency"), currencyList = configuration.currencies)
    future.onComplete{
      case Success(k : Rates) => update(newRates = k)
      case Failure(e) => logger.debug("fetching failed from liveRate client",e)
    }
  }

  private def update(newRates : Rates): Unit ={
    val future = redisClientWrapper.getRates(configuration.currencies)
    future.onComplete{
      case(Success(oldRates)) => configuration.criteria.toList.foreach {
        case (currency, criteriaValue) =>
          val delta = newRates {currency} - oldRates {currency}
          if (delta > criteriaValue) {
            log(currency, delta)
          }
      }
      case(Failure(e : Throwable)) =>
    }
    redisClientWrapper.setRates(newRates).foreach(isSet =>
      logger.debug(s"new HashMap ${newRates.toString} is set into Redis = $isSet"))

  }

  private def log(currency : Currency, delta : Double) : Unit = {
    //todo
  }

}
