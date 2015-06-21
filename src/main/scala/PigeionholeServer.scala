import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import controller.{HostController, LoginController, DashboardController}

/**
 * @author mukai_masaki on 2015/06/19.
 */
class PigeionholeServer extends HttpServer{

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.
      add[DashboardController].
      add[LoginController].
      add[HostController]
  }

}


object PigeionholeBootstrap extends PigeionholeServer
