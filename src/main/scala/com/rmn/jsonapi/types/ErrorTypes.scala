package com.rmn.jsonapi.types

import com.rmn.jsonapi.formatters.{TypeFormatter, SeqLiftFormat}
import com.rmn.jsonapi.models.ErrorObject
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-top-level

@implicitNotFound(
  "If present, 'errors' MUST be an array of error objects.  ${T} did not meet those rules."
)
trait ErrorTypes[T] extends TypeFormatter[T]

object ErrorTypes {
  implicit def errorSeq[T <: ErrorObject[_, _, _, _, _, _, _, _] : Format] = new SeqLiftFormat[T] with ErrorTypes[Seq[T]]
}
