import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

object  MainApp extends App{
   implicit val actorSystem = ActorSystem("live-rates-actor-system")
   val executionContext = actorSystem.dispatcher
  val routes : Route = Routes.routes
  Http().bindAndHandle(routes, "localhost",8080)



}
