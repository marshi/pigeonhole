package controller

import java.rmi.activation.ActivationGroup_Stub

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.util.Config.intoList
import entity.Tables.{HostMachineRow, HostMachine}
import slick.backend.DatabaseConfig
import slick.lifted.Query
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    System.out.println("aiueo")
    val db = Database.forConfig("db")
    val q = HostMachine.result
    val r = db.stream(q)
    r.map(_.foreach(t => println(t.name)))
    println("____")

    response.ok.view("host/list.mustache", null)
  }

  post("/host/register") {request: Request =>
    println(request.params.get("host-name"))
    response.ok.body(request.params.get("host-name"));
  }

}
