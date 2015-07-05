package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import service.{HostBranchService, HostMachineService}

/**
 * @author mukai_masaki on 2015/06/20.
 */
class HostController extends Controller {

  get("/host/list") { request: Request =>
    val hostMachineService = new HostMachineService
    val hostList = hostMachineService.fetchHostSeq()
    response.ok.view("host/list.mustache", hostList)
  }

  delete("/host/delete") { request: Request =>
    val hostId = request.params.getInt("host_id")
    val hostMachineService = new HostMachineService
    val hostBranchService = new HostBranchService
    try {
      hostMachineService.delete(hostId)
      hostBranchService.deleteByHostId(hostId)
      val hostList = hostMachineService.fetchHostSeq()
      response.ok.view("host/list.mustache", hostList);
    } catch {
      case e: Exception => response.internalServerError
    }
  }

}
