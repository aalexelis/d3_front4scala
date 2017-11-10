package controllers

import javax.inject.Inject

import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc._
import play.api.routing.JavaScriptReverseRouter

/**
 * The basic application controller.
 *
 * @param messagesApi The Play messages API.
 * @param webJarAssets The webjar assets implementation.
 */
class ApplicationController @Inject() (
  val messagesApi: MessagesApi,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.PlainTypescriptController.submitForm,
        routes.javascript.ScalaJsTypescriptController.submitForm
      )
    ).as("text/javascript")
  }

}
