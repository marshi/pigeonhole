package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import service.HostBranchService

import scala.util.parsing.json.JSON

/**
 * @author mukai_masaki on 2015/06/25.
 */
class HookController extends Controller {

  /**
   * ブランチ名を取得.
   */
  post("/hook/deploy") { request: Request =>
    val payloads = request.params.get("Payloads")
    val json = payloads match {case Some(p) => JSON.parseFull(p)}
    val map = json.get.asInstanceOf[Map[String, Any]]
    val hostBranchService = new HostBranchService()
    (map.get("host"), map.get("branch")) match {
      case (host: Some[String], branch: Some[String]) => hostBranchService.save(host.get, branch.get)
    }
  }

}
