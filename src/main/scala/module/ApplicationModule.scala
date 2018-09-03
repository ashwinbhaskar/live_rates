package module

import java.io.InputStream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import model.Configuration
import scaldi.Module
import spray.json.JsonParser

import scala.concurrent.ExecutionContext

class ApplicationModule extends Module{
  bind[Config] to ConfigFactory.load()
  bind[Configuration] identifiedBy "configuration" to {
    val stream : InputStream = getClass.getResourceAsStream("/"+ConfigFactory.load().getString("configuration.qa.file"))
    val configuration = scala.io.Source.fromInputStream( stream ).mkString
    JsonParser(configuration).convertTo[Configuration]
  }
  bind[ActorSystem] to ActorSystem("live-rates-actor-system") destroyWith(_.terminate())
  bind[ExecutionContext] to inject[ActorSystem].dispatcher
  bind[ActorMaterializer] to ActorMaterializer()(inject[ActorSystem])
}
