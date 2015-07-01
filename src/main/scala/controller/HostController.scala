package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.validation.NotEmpty
import service.HostMachineService

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    val hostMachineService = new HostMachineService
    val hostList = hostMachineService.fetchHostSeq()
    response.ok.view("host/list.mustache", hostList)
  }

  get("/host/register") { request: Request =>
    response.ok.view("host/register.mustache", null)
  }

  post("/host/register") { request: Request =>
    val hostName = request.params.get("host_name")
    val hostMachineService = new HostMachineService
    try {
      hostMachineService.registerHostMachine(hostName)
      val hostList = hostMachineService.fetchHostSeq()
      response.ok.view("host/list.mustache", hostList);
    } catch {
      case e: IllegalArgumentException => response.badRequest.view("/host/register.mustache", null);
      case e: Exception => response.internalServerError.view("/host/register.mustache", null)
    }
  }

  delete("/host/delete") { request: Request =>
    val hostId = request.params.getInt("host_id")
    val hostMachineService = new HostMachineService
    try {
      hostMachineService.delete(hostId)
      val hostList = hostMachineService.fetchHostSeq()
      response.ok.view("host/list.mustache", hostList);
    } catch {
      case e: Exception => response.internalServerError
    }
  }

}
