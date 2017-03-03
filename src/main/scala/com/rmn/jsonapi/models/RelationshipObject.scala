package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types.{ResourceLinkageTypes, MetaTypes, FullLinkTypes}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class RelationshipObject[Links: FullLinkTypes, Data: ResourceLinkageTypes, Meta: MetaTypes](links: Links, data: Data, meta: Meta)

object RelationshipObject {
  implicit def formats[Links: FullLinkTypes, Data : ResourceLinkageTypes, Meta : MetaTypes]: Format[RelationshipObject[Links, Data, Meta]] = Format[RelationshipObject[Links, Data, Meta]](
    (
      implicitly[TypeFormatter[Links]].readAt(__ \ "links") and
      implicitly[TypeFormatter[Data]].readAt(__ \ "data") and
      implicitly[TypeFormatter[Meta]].readAt  (__ \ "meta")
    )(RelationshipObject.apply[Links, Data, Meta] _),
    (
      implicitly[TypeFormatter[Links]].writeAt(__ \ "links") and
      implicitly[TypeFormatter[Data]].writeAt(__ \ "data") and
      implicitly[TypeFormatter[Meta]].writeAt  (__ \ "meta")
    ).apply(unlift(RelationshipObject.unapply[Links, Data, Meta]))
  )
}
