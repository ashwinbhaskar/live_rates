import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import controller.Routes
import module.ApplicationModule
import scaldi.Injectable._
import service.RateUpdateService

import scala.concurrent.ExecutionContext

object  MainApp extends App{
  implicit val injector = new ApplicationModule
  implicit val actorSystem = inject[ActorSystem]
  implicit val materializer = inject[ActorMaterializer]
  implicit val ec = inject[ExecutionContext]
  implicit val service = inject[RateUpdateService]
  service.updateLiveRate()
  val executionContext = actorSystem.dispatcher
  val routes : Route = new Routes().routes
  Http().bindAndHandle(routes, "localhost",8080)
}
