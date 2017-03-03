package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}
import com.rmn.jsonapi.models.ErrorSource
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#error-objects

@implicitNotFound(
  "If present, an error source object may contain 'pointer' and 'parameter'. ${T} did not meet these rules."
)
trait ErrorSourceTypes[T] extends TypeFormatter[T]

object ErrorSourceTypes {
  implicit val none = new UndefinedFormatter with ErrorSourceTypes[Undefined]

  implicit def yesSource[T <: ErrorSource[_, _] : Format] = new KnownFormat[T] with ErrorSourceTypes[T]

  implicit def liftOption[T: ErrorSourceTypes] = new OptionLiftFormat[T] with ErrorSourceTypes[Option[T]]
}
