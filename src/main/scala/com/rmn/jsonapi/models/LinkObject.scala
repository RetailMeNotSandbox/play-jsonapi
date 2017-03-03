package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types.MetaTypes
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class LinkObject[Meta: MetaTypes](href: String, meta: Meta)

object LinkObject {
  implicit def formats[Meta : MetaTypes]: Format[LinkObject[Meta]] = Format[LinkObject[Meta]](
    (
      (__ \ "href").read[String] and
      implicitly[TypeFormatter[Meta]].readAt  (__ \ "meta")
    )(LinkObject.apply[Meta] _),
    (
      (__ \ "href").write[String] and
      implicitly[TypeFormatter[Meta]].writeAt  (__ \ "meta")
    )(unlift(LinkObject.unapply[Meta]))
  )
}
