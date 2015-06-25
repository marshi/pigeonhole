package service

import java.sql.Timestamp
import java.time.LocalDateTime

import entity.Tables
import infrastructure.DbDriver
import slick.driver.PostgresDriver.api._

import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/25.
 */
class HostBranchService {

  def hostBranchSeq = {
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
   * @param hostName
   * @param branchName
   */
  def save(hostName: Option[String], branchName: Option[String]) = {
    val q = Tables.HostMachine.filter(_.name === hostName).result.head
    val future = DbDriver.db.run(q)
    future.onComplete {
      case Failure(_) => throw new RuntimeException
      case Success(h) =>
        Tables.HostBranch.map(h => (h.hostMachineId, h.branchName, h.deploytime)) +=
          (Some(h.id), branchName, Some(Timestamp.valueOf(LocalDateTime.now())))
    }
  }

}
