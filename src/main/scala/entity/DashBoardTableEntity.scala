package entity

import java.sql.Timestamp

/**
 * @author mukai_masaki on 2015/06/25.
 */
class DashBoardTableEntity
(
  val hostMachineName: Option[String],
  val branchName: Option[String],
  val deployTime: Option[Timestamp]
  )

