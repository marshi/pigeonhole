package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import entity.Tables.{Project, Dashboard, HostMachine}
import infrastructure.DbDriver
import slick.driver.PostgresDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    val q = (Dashboard join HostMachine on (_.hostMachineId === _.id) join Project on ((tuple, p) => tuple._1.projectId === p.id)) map {
      case (tuple, p) => (tuple._2.name, p.name)
    }
    val future = DbDriver.db.run(q.result)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(hm) =>
        response.ok.view("host/list.mustache", hm)
      case Failure(hm) => println("fail")
    }
  }

  post("/host/register") {request: Request =>
    println(request.params.get("host-name"))
    response.ok.body(request.params.get("host-name"));
  }

}
