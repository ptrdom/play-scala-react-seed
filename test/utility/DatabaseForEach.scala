package utility

import org.scalatest.BeforeAndAfterEach
import org.scalatest.Suite

import scala.util.Random

trait DatabaseForEach extends BeforeAndAfterEach with CreateDatabase {
  this: CustomApplicationFactory with Suite =>

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    val databaseName = {
      val random = new Random()
      s"database_${(1 to 10).map(_ => random.nextInt(10)).mkString("")}"
    }
    createDatabase("localhost", 5432, databaseName, "postgres", "postgres")
    this.databaseName = Some(databaseName)
  }

  override protected def afterEach(): Unit = {
    dropDatabase(
      "localhost",
      5432,
      databaseName.getOrElse(
        throw new IllegalStateException("Database name not generated yet!")
      ),
      "postgres",
      "postgres"
    )
    super.afterEach()
  }
}
