package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * @author mukai_masaki on 2015/06/19.
 */
class LoginController extends Controller{

  get("/login") { request: Request =>
    val string = "aiueo"
    response.ok.view("login.mustache", string)
  }

}
