package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import entity.Tables.HostMachine
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
    val q = HostMachine.result
    val future = DbDriver.db.run(q)
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
