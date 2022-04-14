package loader

import com.softwaremill.macwire._
import controllers.AssetsComponents
import controllers.FrontendController
import controllers.HomeController
import controllers.TestDBO
import play.api.Application
import play.api.ApplicationLoader
import play.api.ApplicationLoader.Context
import play.api.BuiltInComponentsFromContext
import play.api.Configuration
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.slick.DbName
import play.api.db.slick.SlickComponents
import play.api.db.slick.evolutions.SlickEvolutionsComponents
import play.filters.HttpFiltersComponents
import router.Routes
import slick.basic.DatabaseConfig
import slick.jdbc.PostgresProfile

class CustomApplicationLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = {
    new CustomComponents(context).application
  }
}

class CustomComponents(context: Context)
    extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with AssetsComponents
    with SlickComponents
    with EvolutionsComponents
    with SlickEvolutionsComponents {
  applicationEvolutions

  lazy val frontendController: FrontendController = wire[FrontendController]
  lazy val homeController: HomeController = wire[HomeController]
  lazy val testDBO: TestDBO = wire[TestDBO]

  lazy val databaseConfig: DatabaseConfig[PostgresProfile] =
    slickApi.dbConfig(DbName("default"))

  lazy val router: Routes = {
    val prefix: String = "/"
    wire[Routes]
  }
}
