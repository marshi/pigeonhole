package service


import entity.Tables.HostBranchRow
import entity.{DashBoardTableEntity, Tables}
import infrastructure.DbDriver
import org.apache.commons.lang.time.FastDateFormat
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/27.
 */
class DashboardService {

  val hostMachineService = new HostMachineService()

  /**
   * ダッシュボードを表示するためのエンティティを返す.
   * @return
   */
  def makeDashboardEntity = {
    val q = Tables.HostBranch.result
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    val dashboardTableEntitySeq = future.value.get match {
      case Success(hostBranches) =>
        hostBranches map {
          case HostBranchRow(_, Some(branchName), hostMachineId, Some(deployTime), Some(hostGroup), Some(username)) =>
            val hostMachineName = hostMachineService.fetchHostMachineName(hostMachineId)
            new DashBoardTableEntity(hostMachineName.get, branchName, username, hostGroup, DashboardService.formatter.format(deployTime))
          case HostBranchRow(_, Some(branchName), hostMachineId, None, Some(hostGroup), Some(username)) =>
            val hostMachineName = hostMachineService.fetchHostMachineName(hostMachineId)
            new DashBoardTableEntity(hostMachineName.get, branchName, username, hostGroup, "")
          case HostBranchRow(_, Some(branchName), hostMachineId, Some(deployTime), None, None) =>
            val hostMachineName = hostMachineService.fetchHostMachineName(hostMachineId)
            new DashBoardTableEntity(hostMachineName.get, branchName, "", "", DashboardService.formatter.format(deployTime))
        }
      case Failure(fb) => throw new RuntimeException
    }
    dashboardTableEntitySeq.sortBy(_.hostMachineName)
  }

}

object DashboardService{
  val formatter = FastDateFormat.getInstance("yyyy/MM/dd HH:mm:ss")
}
