package controllers

import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile

import scala.concurrent.Future

class TestDBO(databaseConfig: DatabaseConfig[PostgresProfile]) {
  import databaseConfig.profile.api._

  def testDb(): Future[Option[String]] = {
    databaseConfig.db.run(sql"SELECT * FROM test_table;".as[String].headOption)
  }
}
