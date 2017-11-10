package utils

import scala.scalajs.js.Date
import scala.scalajs.js.annotation.{ JSExport, JSExportTopLevel }

import shared.DateType

@JSExportTopLevel("scalajs.Validator")
object Validotor extends shared.Validator {

  implicit object JsDateType extends DateType[Date] {
    override def parse(s: String): Date = new Date(Date.parse(s))
    override def formatISO(d: Date): String = d.toISOString()
  }

  @JSExport
  override def isValidEmail(s: String): Boolean = super.isValidEmail(s)

  @JSExport
  override def isValidPhone(s: String): Boolean = super.isValidPhone(s)

  @JSExport
  def isValidDate(s: String): Boolean = super.isValidDate(s)

  @JSExport
  override def maxLength(s: String, length: Int): Boolean = maxLength(s, length)

  @JSExport
  override def minLength(s: String, length: Int): Boolean = super.minLength(s, length)

}