package exception

import akka.http.scaladsl.model.StatusCodes

class LastUpdatedTimeNotAvailbleException(code : Int = StatusCodes.InternalServerError.intValue
                                          ,msg :String = "Last updated time not available") extends ServiceError(code, msg)
