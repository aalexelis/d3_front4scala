package utils

import scala.scalajs.js.Date
import scala.scalajs.js.annotation.{ JSExport, JSExportTopLevel }
import scala.util.Try

@JSExportTopLevel("Utils.Validator")
object Validotor extends shared.Validator {

  @JSExport
  override def isValideEmail(s: String): Boolean = super.isValideEmail(s)

  @JSExport
  override def isValidPhone(s: String): Boolean = isValidPhone(s)

  @JSExport
  override def isValidDate(s: String): Boolean = isValidDate(s)

  @JSExport
  override def maxLength(s: String, length: Int): Boolean = maxLength(s, length)

  @JSExport
  override def minLength(s: String, length: Int): Boolean = super.minLength(s, length)
  
}