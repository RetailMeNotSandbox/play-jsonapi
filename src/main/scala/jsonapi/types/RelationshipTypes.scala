package jsonapi.types

import jsonapi.Undefined
import jsonapi.formatters._
import jsonapi.models.RelationshipObject
import play.api.libs.json.{Format, OFormat}

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-resource-object-relationships
@implicitNotFound(
  "When present, the value of 'relationships' MUST be an object.  The values in the object must be a relationship object.  ${T} did not meet these rules."
)
trait RelationshipTypes[T] extends TypeFormatter[T]

object RelationshipTypes {
  implicit val none = new UndefinedFormatter with RelationshipTypes[Undefined]

  implicit def rawRelationships[T <: RelationshipObject[_, _, _] : Format] = new KnownFormat[Map[String, T]] with RelationshipTypes[Map[String, T]]

  // todo: enforce that all the attributes are <: RelationshipObject
  implicit def refinedRelationships[T: OFormat] = new KnownFormat[T] with RelationshipTypes[T]

  implicit def liftOption[T: RelationshipTypes] = new OptionLiftFormat[T] with RelationshipTypes[Option[T]]
}
