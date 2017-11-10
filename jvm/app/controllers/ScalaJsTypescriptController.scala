package controllers

import javax.inject._

import scala.util.Try

import com.example.{ Email, Name, PhoneNumber, Serializer }
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.i18n.{ I18nSupport, MessagesApi }
import play.api.mvc.{ Action, Controller }
import shared.{ Country }

class ScalaJsTypescriptController @Inject() (
  val messagesApi: MessagesApi,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  import PlainTypescriptController._

  def submitForm = Action(parse.json) { implicit request =>
    val form = request.body.validate[Form]

    form.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      form => {
        Ok(Json.toJson(form))
      }
    )
  }
}

object ScalaJsTypescriptController {

  object Validator extends shared.Validator

  case class Form(name: Name, birthDate: String, email: Email, phone: PhoneNumber, nationality: Country)

  implicit val countryWrites: Writes[Country] = new Writes[Country] {
    def writes(country: Country) = Json.obj(
      "id" -> country.id,
      "label" -> country.label
    )
  }

  object Form extends Serializer {

    implicit val nameReads: Reads[Name] = Reads[Name] {
      case JsString(value) =>
        if (Validator.minLength(value, 1)) {
          JsSuccess(Name(value))
        } else {
          JsError("error.invalid.name")
        }
      case _ => JsError("error.invalid.name")
    }

    implicit val phoneReads: Reads[PhoneNumber] = Reads[PhoneNumber] {
      case JsString(value) =>
        if (Validator.isValidPhone(value)) {
          JsSuccess(PhoneNumber(value))
        } else {
          JsError("error.invalid.phone")
        }
      case _ => JsError("error.invalid.phone")
    }

    implicit val emailReads: Reads[Email] = Reads[Email] {
      case JsString(value) =>
        if (Validator.isValidEmail(value)) {
          JsSuccess(Email(value))
        } else {
          JsError("error.invalid.email")
        }
      case _ => JsError("error.invalid.email")
    }

    implicit val nationalityReads: Reads[Country] = Reads[Country] {
      case JsString(value) =>
        Try {
          val country = shared.Catalog.Countries.filter(country => country.id == value).head
          JsSuccess(country)
        }.getOrElse(JsError("error.invalid.country"))
      case _ => JsError("error.invalid.country")
    }

    implicit val formReads: Reads[Form] = (
      (JsPath \ "name").read[Name] and
      (JsPath \ "birthDate").read[String] and
      (JsPath \ "email").read[Email] and
      (JsPath \ "phone").read[PhoneNumber] and
      (JsPath \ "nationality").read[Country]
    )(Form.apply _)

    implicit val placeWrites: Writes[Form] = (
      (JsPath \ "name").write[Name] and
      (JsPath \ "birthDate").write[String] and
      (JsPath \ "email").write[Email] and
      (JsPath \ "phone").write[PhoneNumber] and
      (JsPath \ "nationality").write[Country]
    )(unlift(Form.unapply))
  }

}