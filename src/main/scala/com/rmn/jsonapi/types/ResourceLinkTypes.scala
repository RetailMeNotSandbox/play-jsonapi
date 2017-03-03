package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}
import com.rmn.jsonapi.models.ResourceLinks
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-resource-object-links

@implicitNotFound(
  "If present, the resource's links object MAY contain a 'self' link.  ${T} did not meet these rules."
)
trait ResourceLinkTypes[T] extends TypeFormatter[T]

object ResourceLinkTypes {
  implicit val none = new UndefinedFormatter with ResourceLinkTypes[Undefined]

  implicit def yesLinks[T <: ResourceLinks[_] : Format] = new KnownFormat[T] with ResourceLinkTypes[T]

  implicit def liftOption[T: ResourceLinkTypes] = new OptionLiftFormat[T] with ResourceLinkTypes[Option[T]]
}
