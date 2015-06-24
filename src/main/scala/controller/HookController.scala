package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * @author mukai_masaki on 2015/06/25.
 */
class HookController extends Controller {

  /**
   * ブランチ名を取得.
   * githubのwebhookと同じ形式でリクエストを受け取ることを想定.
   */
  post("/hook/deploy") { request: Request =>
    val payloads = request
    println(payloads)
//    val json = payloads match {case Some(p) => JSON.parseFull(p)}
//    val map = json.get.asInstanceOf[Map[String, Option[Any]]]
//    val ref = map("ref")
//    println(ref)
  }

}
