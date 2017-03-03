package com.rmn.jsonapi.types

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters._
import com.rmn.jsonapi.models.LinkObject
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-links

@implicitNotFound(
  "If present, a link MUST be represented as either: 1) A string  2) An object with (href: string, meta: Meta).  ${T} did not meet those rules."
)
trait LinkTypes[T] extends TypeFormatter[T]

object LinkTypes {
  implicit val none = new UndefinedFormatter with LinkTypes[Undefined]
  implicit val str = new KnownFormat[String] with LinkTypes[String]

  implicit def obj[T <: LinkObject[_] : Format] = new KnownFormat[T] with LinkTypes[T]

  implicit def liftOption[T](implicit t: LinkTypes[T]) = new OptionLiftFormat[T] with LinkTypes[Option[T]]

  implicit def liftEither[L, R](implicit l: LinkTypes[L], r: LinkTypes[R]) = new EitherLiftFormat[L, R] with LinkTypes[Either[L, R]]
}
