package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}
import com.rmn.jsonapi.models.JsonApiVersion
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-jsonapi-object
@implicitNotFound(
  "A Top Level document MAY include information about its information under 'jsonapi'.  If present, it MUST be an object. ${T} did not meet these rules."
)
trait JsonApiTypes[T] extends TypeFormatter[T]

object JsonApiTypes {
  implicit val none = new UndefinedFormatter with JsonApiTypes[Undefined]

  implicit def implementation[T <: JsonApiVersion[_, _] : Format] = new KnownFormat[T] with JsonApiTypes[T]

  implicit def liftOption[T: JsonApiTypes] = new OptionLiftFormat[T] with JsonApiTypes[Option[T]]
}
