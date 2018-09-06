package logger

import java.io.{File, FileWriter, PrintWriter}
import java.util.Calendar

import Alias.Currency
import org.slf4j.LoggerFactory
import scaldi.{Injectable, Injector}

import scala.concurrent.{ExecutionContext, Future}

class CriteriaExceedReporterToFile(loggingFile : File)(implicit val inj : Injector) extends CriteriaExceedReporter with Injectable{

  val logger = LoggerFactory.getLogger(this.getClass)
  implicit val ec = inject[ExecutionContext]

  private def logAsync(baseCurrency : Currency, currency: Currency, delta : Double) : Unit = {
    Future{
      if(!loggingFile.exists()){
        loggingFile.createNewFile() // Make sure permissions are available
      }
      val writer = new PrintWriter(new FileWriter(loggingFile,true))
      writer.append(s"\n${Calendar.getInstance().getTimeInMillis} $baseCurrency $currency $delta")
      writer.close()
    }.map(_=>logger.info("Successfully logged delta"))
      .recover{
        case ex: Exception => logger.error("error while logging delta",ex)
      }
  }

  override def reportAsync(baseCurrency: Currency, currency: Currency, delta: Double): Unit = logAsync(baseCurrency, currency, delta)
}
