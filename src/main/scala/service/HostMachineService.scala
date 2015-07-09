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

  /**
   * host_machineに指定のホスト名を登録.
   * IDを返却.
   * @param hostName
   * @return ID
   */
  def registerHostMachine(hostName: Option[String]): Int = {
    hostName match {
      case Some(a) if a.isEmpty => throw new IllegalArgumentException
      case _ =>
    }
    val q = Tables.HostMachine.map(_.name) returning Tables.HostMachine.map(_.id) += hostName
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(id) => id
      case Failure(_) => throw new RuntimeException
    }
  }

  def delete(hostId: Option[Int]) = {
    val q = Tables.HostMachine.filter(hm => hm.id === hostId).delete
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
  }

}
