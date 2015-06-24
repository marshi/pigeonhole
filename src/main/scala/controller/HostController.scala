package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import entity.Tables.{Project, Dashboard, HostMachine}
import infrastructure.DbDriver
import service.HostMachineListService
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    val hostMachineListService = new HostMachineListService
    val nameList = hostMachineListService.fetchHostAndProjectName()
    response.ok.view("host/list.mustache", nameList)
  }

  get("/host/register") {request: Request =>
    val future = DbDriver.db.run(Project.result)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(projects) => response.ok.view("host/register.mustache", projects)
      case Failure(projects) =>
    }
  }

  post("/host/register") {request: Request =>
    println(request.params.get("host-name"))
    response.ok.body(request.params.get("host-name"));
  }

}
