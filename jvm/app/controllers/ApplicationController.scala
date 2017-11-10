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

  def appjs = Action.async { implicit request =>
    request.cookies.get("auth") match {
      case Some(cookie) if cookie.value.nonEmpty => controllers.Assets.versioned("/public", "javascripts/dist/members.js")(request)
      case _ => controllers.Assets.versioned("/public", "javascripts/dist/public.js")(request)
    }
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.PlainTypescriptController.submitForm
      )
    ).as("text/javascript")
  }

}
