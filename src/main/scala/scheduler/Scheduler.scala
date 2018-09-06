package scheduler

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import scaldi.{Injectable, Injector}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._


class Scheduler(implicit val injector : Injector) extends Injectable{

  private implicit val ec = inject[ExecutionContext]
  private implicit val system = inject[ActorSystem]
  private val runnables = inject[List[Runnable]] (identified by "SchedulerRunnables")
  def start() : Unit = {
    runnables.foreach(runnable => system.scheduler.schedule(Duration(0,TimeUnit.SECONDS), Duration(5, TimeUnit.SECONDS))(runnable.run()))
  }

}
