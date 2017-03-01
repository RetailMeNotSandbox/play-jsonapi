package jsonapi.types

import jsonapi.Undefined
import jsonapi.formatters._
import play.api.libs.json.{Format, JsObject, OFormat}

import scala.annotation.implicitNotFound

// http://jsonapi.org/format/#document-meta

@implicitNotFound(
  "When present, the value of each 'meta' member MUST be an object. Did you make a Format for your meta class?  Couldn't find one for ${T}"
)
trait MetaTypes[T] extends TypeFormatter[T]

object MetaTypes {
  implicit val none = new UndefinedFormatter with MetaTypes[Undefined]
  implicit val rawMeta = new KnownFormat[JsObject] with MetaTypes[JsObject]

  implicit def refinedMeta[T](implicit fmt: OFormat[T]) = new KnownFormat[T] with MetaTypes[T]

  implicit def liftOption[T: MetaTypes] = new OptionLiftFormat[T] with MetaTypes[Option[T]]
}
