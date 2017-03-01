package jsonapi.models

import jsonapi.formatters.TypeFormatter
import jsonapi.types.{ErrorTypes, JsonApiTypes, MetaTypes}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class TopLevelError[JApi: JsonApiTypes, Err : ErrorTypes, Meta : MetaTypes](
  jsonapi: JApi,
  error: Err,
  meta: Meta
)

object TopLevelError {
  implicit def formats[JApi: JsonApiTypes, Err: ErrorTypes, Meta : MetaTypes] : Format[TopLevelError[JApi, Err, Meta]] = Format[TopLevelError[JApi, Err, Meta]](
    (
      implicitly[TypeFormatter[JApi]].readAt(__ \ "jsonapi") and
      implicitly[TypeFormatter[Err]].readAt(__ \ "errors") and
      implicitly[TypeFormatter[Meta]].readAt(__ \ "meta")
    )(TopLevelError.apply[JApi, Err, Meta] _),
    (
      implicitly[TypeFormatter[JApi]].writeAt(__ \ "jsonapi") and
      implicitly[TypeFormatter[Err]].writeAt(__ \ "errors") and
      implicitly[TypeFormatter[Meta]].writeAt  (__ \ "meta")
    )(unlift(TopLevelError.unapply[JApi, Err, Meta]))
  )
}
