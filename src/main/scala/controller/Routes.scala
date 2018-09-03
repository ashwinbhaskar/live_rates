package controller

import akka.http.scaladsl.server.Directives._
import model.Configuration
import scaldi.{Injectable, Injector}

class Routes(implicit val injector: Injector) extends Injectable {

  val config = inject[Configuration]

  def routes = path("rates"){
    get{
      complete("hello")
    }
  }~
  path("lodha"){
    get{
      complete("lodha")
    }
  }~
  path("configuration"){
    get{
      complete(config)
    }
  }

}
