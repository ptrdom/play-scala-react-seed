package controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

class HomeController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  def appSummary = Action {
    Ok(Json.obj("content" -> "Scala Play React Seed!"))
  }
}
