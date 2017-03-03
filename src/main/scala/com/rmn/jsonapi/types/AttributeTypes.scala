package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}
import play.api.libs.json.{Format, JsObject, OFormat}

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-resource-object-attributes
@implicitNotFound(
  "A Resource Object MAY contain 'attributes'. If present, the value of 'attributes' MUST be an object.  ${T} did not meet these rules."
)
trait AttributeTypes[T] extends TypeFormatter[T]

object AttributeTypes {
  implicit val none = new UndefinedFormatter with AttributeTypes[Undefined]
  implicit val rawAttributes = new KnownFormat[JsObject] with AttributeTypes[JsObject]

  implicit def refinedAttributes[T: OFormat] = new KnownFormat[T] with AttributeTypes[T]

  implicit def liftOption[T: AttributeTypes] = new OptionLiftFormat[T] with AttributeTypes[Option[T]]
}
