package controllers

import org.scalatest.concurrent.IntegrationPatience
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import play.api.test.Helpers._
import play.api.test._
import utility.CustomApplicationFactory
import utility.DatabaseForEach

/** Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  *
  * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
  */
class HomeControllerSpec
    extends PlaySpec
    with BaseOneAppPerTest
    with CustomApplicationFactory
    with ScalaFutures
    with IntegrationPatience
    with DatabaseForEach {

  override val logger: Logger = LoggerFactory.getLogger(this.getClass)

  "HomeController GET" should {

    "render the appSummary resource from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.appSummary().apply(FakeRequest(GET, "/summary"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      val resultJson = contentAsJson(home)
      resultJson.toString() mustBe """{"content":"Scala Play React Seed!"}"""
    }

    "render the appSummary resource from the router" in {
      val request = FakeRequest(GET, "/api/summary")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      val resultJson = contentAsJson(home)
      resultJson.toString() mustBe """{"content":"Scala Play React Seed!"}"""
    }

    "call dbo" in {
      components.testDBO.testDb().futureValue
    }
  }
}
