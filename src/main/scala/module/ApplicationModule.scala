package module

import java.io.{File, InputStream, OutputStream}

import akka.actor.{Actor, ActorSystem}
import akka.stream.ActorMaterializer
import client.{HttpClient, HttpClientImpl, LiveRateClient, LiveRateClientImpl}
import com.typesafe.config.{Config, ConfigFactory}
import logger.CriteriaExceedLogger
import model.Configuration
import scaldi.Module
import scheduler.Scheduler
import service.{CurrencyConfigurationService, RateFetchService, RateFetchServiceImpl, RateUpdateService}
import spray.json.JsonParser
import wrapper.RedisClientWrapper

import scala.concurrent.ExecutionContext

class ApplicationModule extends Module{
  bind[Config] to ConfigFactory.load()
  bind[File]  identifiedBy "criteriaExceedLogFile" to {
    val resource = ConfigFactory.load().getString("configuration.qa.criteriaLogFile")
    new File(System.getProperty("user.home")+"/"+resource)
  }
  bind[Configuration] to {
    val stream : InputStream = getClass.getResourceAsStream("/"+ConfigFactory.load().getString("configuration.qa.configFile"))
    val configuration = scala.io.Source.fromInputStream( stream ).mkString
    JsonParser(configuration).convertTo[Configuration]
  }
  bind[RedisClientWrapper] to new RedisClientWrapper
  bind[ActorSystem] to ActorSystem("live-rates-actor-system") destroyWith(_.terminate())
  bind[ExecutionContext] to inject[ActorSystem].dispatcher
  bind[ActorMaterializer] to ActorMaterializer()(inject[ActorSystem])
  bind[HttpClient] to new HttpClientImpl
  bind[LiveRateClient] to new LiveRateClientImpl
  bind[RateFetchService] to new RateFetchServiceImpl
  bind[RateUpdateService] to new RateUpdateService
  bind[CurrencyConfigurationService] to new CurrencyConfigurationService
  bind[CriteriaExceedLogger] to new CriteriaExceedLogger
  bind[List[Runnable]] identifiedBy "SchedulerRunnables" to List(inject[RateUpdateService])
  bind[Scheduler] to new Scheduler
}
