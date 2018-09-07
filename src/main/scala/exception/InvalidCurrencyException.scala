package exception

import akka.http.scaladsl.model.StatusCodes

class InvalidCurrencyException(code : Int = StatusCodes.BadRequest.intValue, msg : String = "Invalid currency or a currency that does not exist in the system") extends ServiceError(code, msg)
