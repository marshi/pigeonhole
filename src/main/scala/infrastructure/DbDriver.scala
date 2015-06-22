package infrastructure

import slick.driver.PostgresDriver.api._

/**
 * @author mukai_masaki on 2015/06/23.
 */
object DbDriver {

  val db = Database.forConfig("db")

}
