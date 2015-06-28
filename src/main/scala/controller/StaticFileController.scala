package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * @author mukai_masaki on 2015/06/28.
 */
class StaticFileController extends Controller{

  get("/js/:javascript") {request: Request =>
    val jsFile = request.getParam("javascript")
    response.ok.file(s"/js/$jsFile")
  }

  get("/css/:cssFile") {request: Request =>
    val cssFile = request.getParam("javascript")
    response.ok.file(s"/css/$cssFile")
  }

}
