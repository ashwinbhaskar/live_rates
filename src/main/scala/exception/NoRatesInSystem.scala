package exception

import akka.http.scaladsl.model.StatusCodes

class NoRatesInSystem(code: Int = StatusCodes.NoContent.intValue ,msg : String = "No rates are available in the System") extends ServiceError(code,msg)
