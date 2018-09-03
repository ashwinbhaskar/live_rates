package model

import Alias.Currency
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


case class Configuration(criteria : Map[Currency, Double], currencies : List[Currency])
object Configuration extends DefaultJsonProtocol with SprayJsonSupport{
 implicit val requestFormat = jsonFormat(Configuration.apply _,"criteria","currencies")
}