package wrapper

import Alias.{Currency, Rates}
import client.Constants
import com.redis.RedisClient
import com.typesafe.config.Config
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

  def setRates(rates : Rates) : Future[Boolean] = {
   Future{redisClient.hmset(Constants.LIVE_RATE_KEY,rates)}
  }
}
