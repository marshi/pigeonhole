package controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import service.DashboardService

/**
 * @author mukai_masaki on 2015/06/19.
 */
class DashboardController extends Controller {

  val dashboardService = new DashboardService()

  get ("/dashboard") { request: Request =>
    val dashBoardTableEntity = dashboardService.makeDashboardEntity
    response.ok.view("dashboard.mustache", dashBoardTableEntity)
  }

}
