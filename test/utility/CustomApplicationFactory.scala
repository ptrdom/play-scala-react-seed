package utility

import loader.CustomComponents
import org.scalatestplus.play.FakeApplicationFactory
import play.api.Application
import play.api.ApplicationLoader
import play.api.Configuration
import play.api.Environment

trait CustomApplicationFactory extends FakeApplicationFactory {

  protected var databaseName: Option[String] = None

  override def fakeApplication(): Application = {
    val env = Environment.simple()

    val data = Seq(
      databaseName.map("slick.dbs.default.db.databaseName" -> _)
    ).flatten.toMap

    val context = ApplicationLoader.Context.create(env, initialSettings = data)

    val components = new CustomComponents(context)
    _components = Some(components)
    components.application
  }

  private var _components: Option[CustomComponents] = None
  def components: CustomComponents = _components.getOrElse(
    throw new IllegalStateException("Components not constructed yet!")
  )
}
