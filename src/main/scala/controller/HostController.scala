package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.NotEmpty
import service.HostMachineService

case class HostRegisterRequest (@NotEmpty @QueryParam hostName: String)

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    val hostMachineService = new HostMachineService
    val nameList = hostMachineService.fetchHostSeq()
    response.ok.view("host/list.mustache", nameList)
  }

  get("/host/register") { request: Request =>
    response.ok.view("host/register.mustache", null)
  }

  post("/host/register") { request: HostRegisterRequest =>
    println("aiueo")
    val hostName = request.hostName
    val hostMachineService = new HostMachineService
    hostName match {
      case n: String => hostMachineService.registerHostMachine(n)
    }
    val nameList = hostMachineService.fetchHostSeq()
    response.ok.view("host/list.mustache", nameList);
  }


}
