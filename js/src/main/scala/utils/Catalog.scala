package utils

import scala.scalajs.js.annotation.{ JSExport, JSExportTopLevel }
import scala.scalajs.js
import js.JSConverters._

@JSExportTopLevel("scalajs.Catalog")
object Catalog {
  @JSExport
  val Countries = shared.Catalog.Countries.map {
    country => Map("id" -> country.id, "label" -> country.label).toJSDictionary
  }.toJSArray

}