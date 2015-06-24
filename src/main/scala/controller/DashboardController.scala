package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import entity.{DashBoardTableEntity, Tables}
import infrastructure.DbDriver
import service.HostMachineService
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/19.
 */
class DashboardController extends Controller {

  get ("/dashboard") { request: Request =>
    val hostMachineService = new HostMachineService()
    val q = Tables.HostBranch.result
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    val dashBoardTableEntity = future.value.get match {
      case Success(hostBranches) =>
        println(hostBranches)
        hostBranches map { hostBranch =>
          val hostMachineName = hostMachineService.fetchHostMachineName(hostBranch.id)
          new DashBoardTableEntity(hostMachineName, hostBranch.branchName, hostBranch.deploytime)
        }
      case Failure(fb) =>
    }
    response.ok.view("dashboard.mustache", dashBoardTableEntity)
  }

}
