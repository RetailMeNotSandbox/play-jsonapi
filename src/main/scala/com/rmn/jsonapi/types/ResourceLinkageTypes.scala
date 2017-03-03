package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters._
import com.rmn.jsonapi.models.ResourceIdentifier
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-resource-object-linkage

@implicitNotFound(
  "Resource linkage MUST be represented as 1) null 2) empty array 3) a single resource id 4) an array of resource ids. ${T} did not meet these rules."
)
trait ResourceLinkageTypes[T] extends TypeFormatter[T]

object ResourceLinkageTypes {
  implicit val none = new UndefinedFormatter with ResourceLinkageTypes[Undefined]
  implicit def singleLinkage[T <: ResourceIdentifier : Format] = new KnownFormat[T] with ResourceLinkageTypes[T]

  implicit def liftOption[T: ResourceLinkageTypes] = new OptionLiftFormat[T] with ResourceLinkageTypes[Option[T]]
  implicit def liftSeq[T: ResourceLinkageTypes] = new SeqLiftFormat[T] with ResourceLinkageTypes[Seq[T]]
  implicit def liftEither[L: ResourceLinkageTypes, R: ResourceLinkageTypes] = new EitherLiftFormat[L, R] with ResourceLinkageTypes[Either[L, R]]
}
