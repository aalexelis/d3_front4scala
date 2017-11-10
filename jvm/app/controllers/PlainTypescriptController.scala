package controllers

import javax.inject.Inject

import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.{ Action, Controller }

class PlainTypescriptController @Inject() (
  val messagesApi: MessagesApi,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  def submitForm = Action { implicit request =>
    Ok
  }

}
