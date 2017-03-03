package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}
import com.rmn.jsonapi.models.Resource
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-compound-documents
@implicitNotFound(
  "The 'included' field MAY be present, and if present, MUST be represented as an array of Resource Objects.  ${T} did not meet these rules"
)
trait IncludedTypes[T] extends TypeFormatter[T]

object IncludedTypes {
  implicit val none = new UndefinedFormatter  with IncludedTypes[Undefined]

  implicit def resourcesIncluded[T <: Resource[_, _, _, _] : Format] = new KnownFormat[Seq[T]] with IncludedTypes[Seq[T]]

  implicit def liftOption[T: IncludedTypes] = new OptionLiftFormat[T] with IncludedTypes[Option[T]]
}
