package jsonapi.types

import jsonapi.Undefined
import jsonapi.formatters._
import jsonapi.models.ErrorLinks
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#error-objects

@implicitNotFound(
  "If present, an error link object may contain an 'about'.  ${T} did not meet those rules."
)
trait ErrorLinkTypes[T] extends TypeFormatter[T]

object ErrorLinkTypes {
  implicit val none = new UndefinedFormatter with ErrorLinkTypes[Undefined]

  implicit def yesLinks[T <: ErrorLinks[_] : Format] = new KnownFormat[T] with ErrorLinkTypes[T]

  implicit def liftOption[T: ErrorLinkTypes] = new OptionLiftFormat[T] with ErrorLinkTypes[Option[T]]
}
