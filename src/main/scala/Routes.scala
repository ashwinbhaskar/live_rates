import akka.http.scaladsl.server.Directives._

object Routes {

  def routes = path("rates"){
    get{
      complete("hello")
    }
  }~
  path("lodha"){
    get{
      complete("lodha")
    }
  }

}
