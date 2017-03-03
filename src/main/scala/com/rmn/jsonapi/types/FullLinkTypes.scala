package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}
import com.rmn.jsonapi.models.FullLinks
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-resource-object-relationships
// http://jsonapi.org/format/#document-top-level
// both accept self, related, and pagination links

@implicitNotFound(
  "The 'links' member in both top level docs and relationship objects may be present.  ${T} did not meet these rules."
)
trait FullLinkTypes[T] extends TypeFormatter[T]

object FullLinkTypes {
  implicit val none = new UndefinedFormatter with FullLinkTypes[Undefined]

  implicit def yesLinks[T <: FullLinks[_, _, _, _, _, _] : Format] = new KnownFormat[T] with FullLinkTypes[T]

  implicit def liftOption[T: FullLinkTypes] = new OptionLiftFormat[T] with FullLinkTypes[Option[T]]
}
