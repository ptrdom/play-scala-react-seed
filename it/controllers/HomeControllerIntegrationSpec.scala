package controllers

import org.scalatest.concurrent.Eventually
import org.scalatest.concurrent.IntegrationPatience
import org.scalatestplus.play._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import play.api.test.Helpers._
import play.api.test._
import utility.CustomApplicationFactory
import utility.DatabaseForEach

import scala.concurrent.duration.Duration

/** Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class HomeControllerIntegrationSpec
    extends PlaySpec
    with BaseOneServerPerTest
    with OneBrowserPerTest
    with ChromeFactory
    with IntegrationPatience
    with Eventually
    with CustomApplicationFactory
    with DatabaseForEach {

  override val logger: Logger = LoggerFactory.getLogger(this.getClass)

  implicit def patience: PatienceConfig = PatienceConfig(Duration.Inf)

  "HomeController" should {

    "render" in {
      val host = "http://localhost:" + port
      println(s"host [$host]")
      go to (host)
      eventually {
        pageSource must include("Scala Play React Seed!")
      }
    }
  }
}
