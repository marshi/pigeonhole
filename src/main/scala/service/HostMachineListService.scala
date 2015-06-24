package service

import entity.Tables
import infrastructure.DbDriver
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 *
 * @author mukai_masaki on 2015/06/23.
 */
class HostMachineListService {

  def fetchHostAndProjectName(): Seq[(Option[String], String)] = {
    val q = (Tables.Dashboard join Tables.HostMachine on (_.hostMachineId === _.id) join Tables.Project on ((tuple, p) => tuple._1.projectId === p.id)) map {
      case (tuple, p) => (tuple._2.name, p.name)
    }
    val future = DbDriver.db.run(q.result)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(hm) => hm
      case Failure(hm) => Nil
    }
  }

}
