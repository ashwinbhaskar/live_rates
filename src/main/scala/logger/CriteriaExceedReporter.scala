package logger

import Alias.Currency

trait CriteriaExceedReporter {
  def reportAsync(baseCurrency : Currency, currency : Currency, delta : Double)
}
