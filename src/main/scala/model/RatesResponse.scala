package model



import Alias.{Currency, Rates}
import spray.json.DefaultJsonProtocol

case class RatesResponse(timeStamp : Long, baseCurrency : Currency, rates : Rates)

object RatesResponse extends DefaultJsonProtocol{

  implicit val formatter = jsonFormat(RatesResponse.apply _,"timestamp","base","rates")
}
