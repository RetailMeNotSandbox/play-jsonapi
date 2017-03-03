package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types.{MetaTypes, MaybeString}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class JsonApiVersion[Version: MaybeString, Meta : MetaTypes](version: Version, meta: Meta)

object JsonApiVersion {
  implicit def formats[Version: MaybeString, Meta : MetaTypes] : Format[JsonApiVersion[Version, Meta]] = Format[JsonApiVersion[Version, Meta]](
    (
      implicitly[TypeFormatter[Version]].readAt(__ \ "version") and
      implicitly[TypeFormatter[Meta]].readAt  (__ \ "meta")
    )(JsonApiVersion.apply[Version, Meta] _),
    (
      implicitly[TypeFormatter[Version]].writeAt(__ \ "version") and
      implicitly[TypeFormatter[Meta]].writeAt  (__ \ "meta")
    )(unlift(JsonApiVersion.unapply[Version, Meta]))
  )
}
