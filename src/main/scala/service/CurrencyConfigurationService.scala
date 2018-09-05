package service

import Alias.Currency
import exception.InvalidCurrencyException
import model.Configuration
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}


class CurrencyConfigurationService(implicit val inj : Injector) extends Injectable {
  implicit val ec = inject[ExecutionContext]
  implicit val configuration = inject[Configuration]

  def validateCurrencies(currencyList : List[Currency]) : Future[Unit] = {
    Future{
      if(currencyList.exists(someCurrency => !configuration.currencies.contains(someCurrency))){
        throw new InvalidCurrencyException
      }
    }
  }


}
