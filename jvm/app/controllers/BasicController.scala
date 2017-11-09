package controllers

import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.pickling.{ Pickler, Unpickler }
import scala.language.higherKinds
import scala.pickling.json.JSONPickle

case class Catalog(id: String, text: String, active: Boolean, parentId: Option[String] = None) extends Payload

trait Payload {
  def toPickle: JSONPickle = Payload.toPickle(this)
  def serialize: String = toPickle.value
  def toJson: JsValue = Json.parse(serialize)
}

object Payload {
  import scala.pickling.Defaults._, scala.pickling.json._
  import scala.pickling.{ FastTypeTag, PBuilder, PReader, PicklingException }
  type PrimitivePicklers = scala.pickling.pickler.PrimitivePicklers
  type PrimitiveArrayPicklers = scala.pickling.pickler.PrimitiveArrayPicklers
  type RefPicklers = scala.pickling.pickler.RefPicklers

  implicit def optionPickler[A: FastTypeTag](implicit elemPickler: Pickler[A], elemUnpickler: Unpickler[A], collTag: FastTypeTag[Option[A]]): Pickler[Option[A]] with Unpickler[Option[A]] =
    new Pickler[Option[A]] with Unpickler[Option[A]] {
      private implicit val elemTag = implicitly[FastTypeTag[A]]
      val tag = implicitly[FastTypeTag[Option[A]]]
      private val isPrimitive = elemTag.isEffectivelyPrimitive
      private val nullTag = implicitly[FastTypeTag[Null]]
      def pickle(coll: Option[A], builder: PBuilder): Unit = {
        // Here we cheat the "entry" so that the notion of option
        // is erased for "null"
        coll match {
          case Some(elem) =>
            builder.hintTag(elemTag)
            builder.hintStaticallyElidedType()
            elemPickler.pickle(elem, builder)
          case None =>
            builder.hintTag(nullTag)
            builder.hintStaticallyElidedType()
            elemPickler.pickle(null.asInstanceOf[A], builder)
        }
      }
      def unpickle(tag: String, preader: PReader): Any = {
        // Note - if we call beginEntry we should see JNothing or JNull show up if the option is empty.
        val reader = preader.beginCollection()
        preader.pushHints()
        if (isPrimitive) {
          reader.hintStaticallyElidedType()
          reader.hintTag(elemTag)
          reader.pinHints()
        } else reader.hintTag(elemTag)
        val length = reader.readLength
        val result: Option[A] =
          if (length == 0) None
          else {
            val elem = elemUnpickler.unpickleEntry(reader.readElement())
            Some(elem.asInstanceOf[A])
          }
        if (isPrimitive) preader.unpinHints()
        preader.popHints()
        reader.endCollection()
        result
      }
    }

  def toPickle(self: Payload) = self match {
    case p: Catalog => p.pickle
  }
}

trait JSendData

case class BareStringData(data: String) extends JSendData

case class BareObjectData[T <: Payload](data: T) extends JSendData

case class BareArrayData[T <: Payload](data: Seq[T]) extends JSendData

case class MapObjectData[T <: Payload](data: Map[String, T]) extends JSendData

case class MapArrayData[T <: Payload](data: Map[String, Seq[T]]) extends JSendData

object JSendStatus {
  def getFromCode(code: String): Option[JSendStatus] = values.find(_.code == code)

  case object Success extends JSendStatus("success")

  case object Error extends JSendStatus("error")

  case object Fail extends JSendStatus("fail")

  val values = Array(Success, Error, Fail)
}

sealed abstract class JSendStatus(val code: String) {
  val name = toString
}

//refer to JSend standard in order to see how to populate this class
case class JSend(status: JSendStatus, data: JSendData = null, message: Option[String] = None, code: Option[Int] = None) {
  def toJson(): JsValue = {
    implicit val mapStringPayload: Writes[JSendData] = new Writes[JSendData] {

      def writes(p: JSendData) = p match {
        case v: BareStringData => JsString(v.data.toString)
        case v: BareObjectData[_] => v.data.toJson
        case v: BareArrayData[_] => JsArray(v.data.map(_.toJson))
        case v: MapObjectData[_] => JsObject(v.data.map(t => t._1 -> t._2.toJson))
        case v: MapArrayData[_] => JsObject(v.data.map(t => t._1 -> JsArray(t._2.map(_.toJson))))
        case _ => JsNull
      }
    }

    implicit val jSendStatus: Writes[JSendStatus] = new Writes[JSendStatus] {
      def writes(j: JSendStatus) = JsString(j.code)
    }

    implicit val jSendToJson: Writes[JSend] = (
      (JsPath \ "status").write[JSendStatus] and
      (JsPath \ "data").write[JSendData] and
      (JsPath \ "message").writeNullable[String] and
      (JsPath \ "code").writeNullable[Int]
    )(unlift(JSend.unapply))

    Json.toJson(this)
  }
}
