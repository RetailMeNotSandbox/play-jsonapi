package jsonapi.types

import jsonapi.Undefined
import jsonapi.formatters._
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

@implicitNotFound(
  "This type is limited to String and Option[String].  ${T} was neither."
)
trait MaybeString[T] extends TypeFormatter[T]

object MaybeString {
  implicit val none = new UndefinedFormatter with MaybeString[Undefined]

  implicit val definiteString = new KnownFormat[String] with MaybeString[String]
  implicit val maybeString = new OptionLiftFormat[String] with MaybeString[Option[String]]

}
