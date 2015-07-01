package service

import entity.Tables
import entity.Tables.HostMachineRow
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

  def fetchHostSeq(): Seq[HostMachineRow] = {
    val q = Tables.HostMachine.result
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(hmSeq) => hmSeq
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

  def registerHostMachine(hostName: Option[String]) = {
    hostName match {
      case Some(a) if a.isEmpty => throw new IllegalArgumentException
    }
    val q = Tables.HostMachine.map(hm => hm.name) += hostName
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
  }

  def delete(hostId: Option[Int]) = {
    val q = Tables.HostMachine.filter(hm => hm.id === hostId) delete
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
  }

}
