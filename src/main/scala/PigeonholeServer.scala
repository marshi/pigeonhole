import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import controller.{StaticFileController, DashboardController, HookController, HostController}

/**
 * @author mukai_masaki on 2015/06/19.
 */
class PigeonholeServer extends HttpServer{

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.
      add[DashboardController].
      add[HostController].
      add[HookController].
      add[StaticFileController].
      filter[CommonFilters]
  }

}


object PigeonholeBootstrap extends PigeonholeServer
