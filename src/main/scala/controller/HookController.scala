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
   * ブランチ名、ホスト名を指定してDBを更新.
   */
  post("/hook/deploy") { request: Request =>
    val payloads = request.params.get("Payloads")
    val json = payloads match {case Some(p) => JSON.parseFull(p)}
    val map = json.get.asInstanceOf[Map[String, Any]]
    val hostBranchService = new HostBranchService()
    (map.get("host"), map.get("branch"), map.get("hostGroup"), map.get("username")) match {
      case (Some(host: String), Some(branch: String), Some(hostGroup: String), Some(username: String)) =>
        try {
          hostBranchService.save(host, branch, hostGroup, username)
        } catch {
          case e: Exception => response.internalServerError
        }
    }
    response.ok
  }

}
