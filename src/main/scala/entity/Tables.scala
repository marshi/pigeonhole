package entity
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = HostBranch.schema ++ HostMachine.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table HostBranch
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param branchName Database column branch_name SqlType(varchar), Length(128,true), Default(None)
   *  @param hostMachineId Database column host_machine_id SqlType(int4)
   *  @param deployTime Database column deploy_time SqlType(timestamptz), Default(None)
   *  @param hostGroup Database column host_group SqlType(varchar), Length(128,true), Default(None)
   *  @param username Database column username SqlType(varchar), Length(32,true), Default(None) */
  case class HostBranchRow(id: Int, branchName: Option[String] = None, hostMachineId: Int, deployTime: Option[java.sql.Timestamp] = None, hostGroup: Option[String] = None, username: Option[String] = None)
  /** GetResult implicit for fetching HostBranchRow objects using plain SQL queries */
  implicit def GetResultHostBranchRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[HostBranchRow] = GR{
    prs => import prs._
    HostBranchRow.tupled((<<[Int], <<?[String], <<[Int], <<?[java.sql.Timestamp], <<?[String], <<?[String]))
  }
  /** Table description of table host_branch. Objects of this class serve as prototypes for rows in queries. */
  class HostBranch(_tableTag: Tag) extends Table[HostBranchRow](_tableTag, "host_branch") {
    def * = (id, branchName, hostMachineId, deployTime, hostGroup, username) <> (HostBranchRow.tupled, HostBranchRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), branchName, Rep.Some(hostMachineId), deployTime, hostGroup, username).shaped.<>({r=>import r._; _1.map(_=> HostBranchRow.tupled((_1.get, _2, _3.get, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column branch_name SqlType(varchar), Length(128,true), Default(None) */
    val branchName: Rep[Option[String]] = column[Option[String]]("branch_name", O.Length(128,varying=true), O.Default(None))
    /** Database column host_machine_id SqlType(int4) */
    val hostMachineId: Rep[Int] = column[Int]("host_machine_id")
    /** Database column deploy_time SqlType(timestamptz), Default(None) */
    val deployTime: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("deploy_time", O.Default(None))
    /** Database column host_group SqlType(varchar), Length(128,true), Default(None) */
    val hostGroup: Rep[Option[String]] = column[Option[String]]("host_group", O.Length(128,varying=true), O.Default(None))
    /** Database column username SqlType(varchar), Length(32,true), Default(None) */
    val username: Rep[Option[String]] = column[Option[String]]("username", O.Length(32,varying=true), O.Default(None))

    /** Uniqueness Index over (hostMachineId) (database name host_branch_host_machine_id_key) */
    val index1 = index("host_branch_host_machine_id_key", hostMachineId, unique=true)
  }
  /** Collection-like TableQuery object for table HostBranch */
  lazy val HostBranch = new TableQuery(tag => new HostBranch(tag))

  /** Entity class storing rows of table HostMachine
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(64,true) */
  case class HostMachineRow(id: Int, name: String)
  /** GetResult implicit for fetching HostMachineRow objects using plain SQL queries */
  implicit def GetResultHostMachineRow(implicit e0: GR[Int], e1: GR[String]): GR[HostMachineRow] = GR{
    prs => import prs._
    HostMachineRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table host_machine. Objects of this class serve as prototypes for rows in queries. */
  class HostMachine(_tableTag: Tag) extends Table[HostMachineRow](_tableTag, "host_machine") {
    def * = (id, name) <> (HostMachineRow.tupled, HostMachineRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name)).shaped.<>({r=>import r._; _1.map(_=> HostMachineRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(64,true) */
    val name: Rep[String] = column[String]("name", O.Length(64,varying=true))

    /** Uniqueness Index over (name) (database name host_machine_name_key) */
    val index1 = index("host_machine_name_key", name, unique=true)
  }
  /** Collection-like TableQuery object for table HostMachine */
  lazy val HostMachine = new TableQuery(tag => new HostMachine(tag))
}
