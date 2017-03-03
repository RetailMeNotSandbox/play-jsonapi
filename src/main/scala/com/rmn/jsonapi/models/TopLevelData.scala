package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types._
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class TopLevelData[JApi: JsonApiTypes, Data: PrimaryDataTypes, Meta : MetaTypes, Links: FullLinkTypes, Included: IncludedTypes](
  jsonapi: JApi,
  data: Data,
  meta: Meta,
  links: Links,
  included: Included
)

object TopLevelData {
  implicit def formats[JApi: JsonApiTypes, Data: PrimaryDataTypes, Meta : MetaTypes, Links: FullLinkTypes, Included: IncludedTypes] : Format[TopLevelData[JApi, Data, Meta, Links, Included]] = {
    Format[TopLevelData[JApi, Data, Meta, Links, Included]](
      (
        implicitly[TypeFormatter[JApi]].readAt(__ \ "jsonapi") and
        implicitly[TypeFormatter[Data]].readAt(__ \ "data") and
        implicitly[TypeFormatter[Meta]].readAt(__ \ "meta") and
        implicitly[TypeFormatter[Links]].readAt(__ \ "links") and
        implicitly[TypeFormatter[Included]].readAt(__ \ "included")
      )(TopLevelData.apply[JApi, Data, Meta, Links, Included] _),
      (
        implicitly[TypeFormatter[JApi]].writeAt(__ \ "jsonapi") and
        implicitly[TypeFormatter[Data]].writeAt(__ \ "data") and
        implicitly[TypeFormatter[Meta]].writeAt(__ \ "meta") and
        implicitly[TypeFormatter[Links]].writeAt(__ \ "links") and
        implicitly[TypeFormatter[Included]].writeAt(__ \ "included")
      )(unlift(TopLevelData.unapply[JApi, Data, Meta, Links, Included]))
    )
  }
}
