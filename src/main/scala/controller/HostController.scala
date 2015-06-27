package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import entity.Tables
import infrastructure.DbDriver
import service.HostMachineService
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    val hostMachineListService = new HostMachineService
    val nameList = hostMachineListService.fetchHostAndProjectName()
    response.ok.view("host/list.mustache", nameList)
  }

  get("/host/register") {request: Request =>
    val future = DbDriver.db.run(Tables.HostProject.result)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(projects) => response.ok.view("host/register.mustache", projects)
      case Failure(projects) =>
    }
  }

  post("/host/register") {request: Request =>
    response.ok.body(request.params.get("host-name"));
  }

}
