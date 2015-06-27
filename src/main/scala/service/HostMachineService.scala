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
class HostMachineService {

  def fetchHostSeq(): Seq[Option[String]] = {
    val q = Tables.HostMachine.result
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(hmSeq) => hmSeq.map {hm => hm.name}
      case Failure(hm) => Nil
    }
  }

  def fetchHostMachineName(hostMachineId: Int): Option[String] = {
    val q = Tables.HostMachine.filter(_.id === hostMachineId).map(_.name).result.head
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(hostMachineName) => hostMachineName
      case Failure(hostMachineName) => None
    }
  }

  def registerHostMachine(hostName: String) = {
    val q = Tables.HostMachine.map(hm => hm.name) += Some(hostName)
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
  }

}
