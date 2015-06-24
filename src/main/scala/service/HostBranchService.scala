package service

import entity.Tables
import infrastructure.DbDriver

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import slick.driver.PostgresDriver.api._

/**
 * @author mukai_masaki on 2015/06/25.
 */
class HostBranchService {

  def hostBranchSeq = {
    val q = Tables.HostBranch.result
    val future = DbDriver.db.run(q)
    Await.ready(future, Duration.Inf)
    future.value.get match {
      case Success(hbSeq) =>
        hbSeq map { hb =>

        }
      case Failure(fb) => Nil
    }
  }

}
