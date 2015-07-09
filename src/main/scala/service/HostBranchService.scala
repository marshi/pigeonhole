package service

import java.sql.Timestamp
import java.time.LocalDateTime

import entity.Tables
import infrastructure.DbDriver
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/25.
 */
class HostBranchService {

  def hostBranchSeq() = {
    val q = Tables.HostBranch.result
    val future = DbDriver.db.run(q)
    future.onComplete {
      case Success(hbSeq) =>
        hbSeq map { hb =>

        }
      case Failure(fb) => Nil
    }
  }

  /**
   * 対象のホスト名、ブランチ名に対応した値を永続化.
   * ホスト名が登録されていない場合、host_machineテーブルにinsertしたうえでhost_branchを更新.
   * @param hostName
   * @param branchName
   */
  def save(hostName: String, branchName: String) = {
    val hostMachineQuery = Tables.HostMachine.filter(_.name === hostName).result.head
    val fetchingMostMachineFuture = DbDriver.db.run(hostMachineQuery)
    Await.ready(fetchingMostMachineFuture, Duration.Inf)

    val hostMachineId = fetchingMostMachineFuture.value.get match {
      case Failure(_) => //host_machineに指定のホスト名が登録されていない場合
        val hostMachineService = new HostMachineService
        hostMachineService.registerHostMachine(Some(hostName))
      case Success(hm) => hm.id
    }
    val selectQuery = Tables.HostBranch.filter(hb =>
      hb.hostMachineId === hostMachineId).exists.result
    val branchHostFuture = DbDriver.db.run(selectQuery)
    Await.ready(branchHostFuture, Duration.Inf)
    val exists = branchHostFuture.value.get match {
      case Failure(_) => throw new RuntimeException
      case Success(exist) => exist
    }
    if (exists) {
      val q = Tables.HostBranch.filter(hb =>
        hb.hostMachineId === hostMachineId).
        map(hb => (hb.hostMachineId, hb.branchName, hb.deployTime)).
        update(Some(hostMachineId), Some(branchName), Some(Timestamp.valueOf(LocalDateTime.now())))
      DbDriver.db.run(q).onComplete {
        case Failure(_) => throw new RuntimeException
        case Success(t) => println(t.value)
      }
    } else {
      val q = Tables.HostBranch.map(h => (h.hostMachineId, h.branchName, h.deployTime)) += (Some(hostMachineId), Some(branchName), Some(Timestamp.valueOf(LocalDateTime.now())))
      DbDriver.db.run(q).onComplete {
        case Failure(_) => throw new RuntimeException
        case Success(t) => println(t.value)
      }
    }
  }

  def deleteByHostId(hostId: Option[Int]) = {
    val q = Tables.HostBranch.filter(_.hostMachineId === hostId).delete
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
  }

}

