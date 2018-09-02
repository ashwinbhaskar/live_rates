import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

object  MainApp extends App{
   implicit val actorSystem = ActorSystem("live-rates-actor-system")
  implicit val materializer = ActorMaterializer()
   val executionContext = actorSystem.dispatcher
  val routes : Route = Routes.routes
  Http().bindAndHandle(routes, "localhost",8080)



}
