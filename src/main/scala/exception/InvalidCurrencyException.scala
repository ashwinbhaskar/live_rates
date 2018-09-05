package exception

class InvalidCurrencyException(code : Int = 422, msg : String = "Invalid currency or a currency that does not exist in the system") extends ServiceError(code, msg)
