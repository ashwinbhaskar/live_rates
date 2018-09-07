package wrapper

import Alias.{Currency, Rates}
import client.Constants
import com.redis.RedisClient
import com.typesafe.config.Config
import exception.LastUpdatedTimeNotAvailbleException
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}

class RedisClientWrapper(implicit val inj : Injector) extends Injectable{
  val config = inject[Config]
  implicit val ec = inject[ExecutionContext]
  val redisHost = config.getString("configuration.qa.redisHost")
  val redisPort = config.getString("configuration.qa.redisPort")
  private val redisClient = new RedisClient(host = redisHost, port = redisPort.toInt)

  def getRates(list : List[Currency]) : Future[Rates] = {
    redisClient.hmget(Constants.LIVE_RATE_KEY,list:_*) match {
      case Some(rates) =>Future{
        rates.toList.map(k => (k._1,k._2.toDouble)).toMap
      }
      case None => Future{Map[String, Double]()}
    }
  }

  def getLastUpdatedTimeStamp : Future[Long] = Future{
    redisClient.get(Constants.LAST_UPDATED_TIME) match {
      case Some(time) => time.toLong
      case None => throw new LastUpdatedTimeNotAvailbleException
    }
  }

  def setRates(rates : Rates, timeStamp : Long) : Future[Boolean] =
    setRates(rates).map(_ && redisClient.set(Constants.LAST_UPDATED_TIME, timeStamp))


  private def setRates(rates : Rates) : Future[Boolean] = {
   Future{redisClient.hmset(Constants.LIVE_RATE_KEY,rates)}
  }
}
