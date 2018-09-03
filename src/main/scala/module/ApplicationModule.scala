package module

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import scaldi.Module

import scala.concurrent.ExecutionContext

class ApplicationModule extends Module{
  bind[Config] to ConfigFactory.load()
  bind[ActorSystem] to ActorSystem("live-rates-actor-system") destroyWith(_.terminate())
  bind[ExecutionContext] to inject[ActorSystem].dispatcher
  bind[ActorMaterializer] to ActorMaterializer()(inject[ActorSystem])
}
