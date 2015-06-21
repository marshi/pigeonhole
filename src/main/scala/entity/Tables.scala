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
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Dashboard.schema ++ HostMachine.schema ++ Project.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Dashboard
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param hostMachineId Database column host_machine_id SqlType(int4), Default(None)
   *  @param projectId Database column project_id SqlType(int4), Default(None) */
  case class DashboardRow(id: Int, hostMachineId: Option[Int] = None, projectId: Option[Int] = None)
  /** GetResult implicit for fetching DashboardRow objects using plain SQL queries */
  implicit def GetResultDashboardRow(implicit e0: GR[Int], e1: GR[Option[Int]]): GR[DashboardRow] = GR{
    prs => import prs._
    DashboardRow.tupled((<<[Int], <<?[Int], <<?[Int]))
  }
  /** Table description of table dashboard. Objects of this class serve as prototypes for rows in queries. */
  class Dashboard(_tableTag: Tag) extends Table[DashboardRow](_tableTag, "dashboard") {
    def * = (id, hostMachineId, projectId) <> (DashboardRow.tupled, DashboardRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), hostMachineId, projectId).shaped.<>({r=>import r._; _1.map(_=> DashboardRow.tupled((_1.get, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column host_machine_id SqlType(int4), Default(None) */
    val hostMachineId: Rep[Option[Int]] = column[Option[Int]]("host_machine_id", O.Default(None))
    /** Database column project_id SqlType(int4), Default(None) */
    val projectId: Rep[Option[Int]] = column[Option[Int]]("project_id", O.Default(None))
  }
  /** Collection-like TableQuery object for table Dashboard */
  lazy val Dashboard = new TableQuery(tag => new Dashboard(tag))

  /** Entity class storing rows of table HostMachine
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(64,true), Default(None) */
  case class HostMachineRow(id: Int, name: Option[String] = None)
  /** GetResult implicit for fetching HostMachineRow objects using plain SQL queries */
  implicit def GetResultHostMachineRow(implicit e0: GR[Int], e1: GR[Option[String]]): GR[HostMachineRow] = GR{
    prs => import prs._
    HostMachineRow.tupled((<<[Int], <<?[String]))
  }
  /** Table description of table host_machine. Objects of this class serve as prototypes for rows in queries. */
  class HostMachine(_tableTag: Tag) extends Table[HostMachineRow](_tableTag, "host_machine") {
    def * = (id, name) <> (HostMachineRow.tupled, HostMachineRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name).shaped.<>({r=>import r._; _1.map(_=> HostMachineRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(64,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(64,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table HostMachine */
  lazy val HostMachine = new TableQuery(tag => new HostMachine(tag))

  /** Entity class storing rows of table Project
   *  @param name Database column name SqlType(varchar), Length(64,true)
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey */
  case class ProjectRow(name: String, id: Int)
  /** GetResult implicit for fetching ProjectRow objects using plain SQL queries */
  implicit def GetResultProjectRow(implicit e0: GR[String], e1: GR[Int]): GR[ProjectRow] = GR{
    prs => import prs._
    ProjectRow.tupled((<<[String], <<[Int]))
  }
  /** Table description of table project. Objects of this class serve as prototypes for rows in queries. */
  class Project(_tableTag: Tag) extends Table[ProjectRow](_tableTag, "project") {
    def * = (name, id) <> (ProjectRow.tupled, ProjectRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(name), Rep.Some(id)).shaped.<>({r=>import r._; _1.map(_=> ProjectRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column name SqlType(varchar), Length(64,true) */
    val name: Rep[String] = column[String]("name", O.Length(64,varying=true))
    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Project */
  lazy val Project = new TableQuery(tag => new Project(tag))
}
