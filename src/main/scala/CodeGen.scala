/**
 * @author mukai_masaki on 2015/06/20.
 */
object CodeGen {

  def main(args: Array[String]) {
    slick.codegen.SourceCodeGenerator.main(
      Array(
        "slick.driver.PostgresDriver",
        "org.postgresql.Driver",
        "jdbc:postgresql://localhost/postgres",
        "src/main/scala",
        "entity",
        "postgres",
        "postgres")
    )
  }

}
