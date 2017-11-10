package controllers

import javax.inject._
import org.joda.time.{ DateTime, LocalDate }
import play.api.libs.json._
import play.api.libs.functional.syntax._

import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.{ Action, Controller }

class PlainTypescriptController @Inject() (
  val messagesApi: MessagesApi,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  case class Form(name: String, birthDate: String, email: String, phone: String, nationality: String)

  implicit val formReads: Reads[Form] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "birthDate").read[String] and
    (JsPath \ "email").read[String] and
    (JsPath \ "phone").read[String] and
    (JsPath \ "nationality").read[String]
  )(Form.apply _)

  def submitForm = Action { implicit request =>

    val res = for {
      json <- request.body.asJson
    } yield {

    }
    Ok
  }

}
