package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * @author mukai_masaki on 2015/07/06.
 */
class HowtouseController extends Controller {

  get("/howtouse") { request: Request =>
    response.ok.view("howtouse.mustache", null)
  }

}
