package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters._
import com.rmn.jsonapi.models.{Resource, ResourceIdentifier}
import play.api.libs.json.Format

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-top-level

@implicitNotFound(
  "Primary data MUST be 1) a single resource 2) an array of resources 3) a single resource id 4) an array of resource ids 5) null 6) an empty array. ${T} did not meet these rules."
)
trait PrimaryDataTypes[T] extends TypeFormatter[T]

object PrimaryDataTypes {
  implicit val none = new UndefinedFormatter with PrimaryDataTypes[Undefined]

  implicit def singleResource[T <: Resource[_, _, _, _] : Format] = new KnownFormat[T] with PrimaryDataTypes[T]

  implicit def singleResourceId[T <: ResourceIdentifier : Format] = new KnownFormat[T] with PrimaryDataTypes[T]

  implicit def lifSeq[T: PrimaryDataTypes] = new SeqLiftFormat[T] with PrimaryDataTypes[Seq[T]]

  implicit def liftOption[T: PrimaryDataTypes] = new OptionLiftFormat[T] with PrimaryDataTypes[Option[T]]

  implicit def liftEither[L: PrimaryDataTypes, R: PrimaryDataTypes] = new EitherLiftFormat[L, R] with PrimaryDataTypes[Either[L, R]]
}
