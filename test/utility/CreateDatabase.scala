package utility

import org.slf4j.Logger
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

trait CreateDatabase {

  val logger: Logger

  private val actionTimeout = 10.second
  private val driver = "org.postgresql.Driver"

  def createDatabase(
      host: String,
      port: Int,
      dbName: String,
      user: String,
      pwd: String
  ): Unit = {
    val onlyHostNoDbUrl = s"jdbc:postgresql://$host:$port/"
    using(
      Database
        .forURL(onlyHostNoDbUrl, user = user, password = pwd, driver = driver)
    ) { conn =>
      logger.info(s"Creating database [$dbName]")
      Await.result(conn.run(sqlu"CREATE DATABASE #$dbName"), actionTimeout)
      ()
    }
  }

  def dropDatabase(
      host: String,
      port: Int,
      dbName: String,
      user: String,
      pwd: String
  ): Unit = {
    val onlyHostNoDbUrl = s"jdbc:postgresql://$host:$port/"
    using(
      Database
        .forURL(onlyHostNoDbUrl, user = user, password = pwd, driver = driver)
    ) { conn =>
      logger.info(s"Dropping database [$dbName]")
      Await.result(
        conn.run(sqlu"DROP DATABASE #$dbName WITH (FORCE)"),
        actionTimeout
      )
      ()
    }
  }

  private def using[B](
      resource: PostgresProfile.backend.DatabaseDef
  )(f: PostgresProfile.backend.DatabaseDef => B): B =
    try {
      f(resource)
    } finally {
      Try {
        resource.close()
      }.failed.foreach(err =>
        throw new Exception(s"failed to close $resource", err))
    }
}
