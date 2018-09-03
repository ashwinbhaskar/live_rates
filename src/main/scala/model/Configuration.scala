package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class Configuration(criteria : Map[String, Double], currencies : List[String])
object Configuration extends DefaultJsonProtocol with SprayJsonSupport{
 implicit val requestFormat = jsonFormat(Configuration.apply _,"criteria","currencies")
}