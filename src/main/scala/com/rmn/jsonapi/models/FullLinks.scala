package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types.LinkTypes
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class FullLinks[Self: LinkTypes, Related: LinkTypes, First: LinkTypes, Last: LinkTypes, Prev: LinkTypes, Next: LinkTypes](
  self: Self,
  related: Related,
  first: First,
  last: Last,
  prev: Prev,
  next: Next
)

object FullLinks {
  implicit def formats[Self: LinkTypes, Related: LinkTypes, First: LinkTypes, Last: LinkTypes, Prev: LinkTypes, Next: LinkTypes] : Format[FullLinks[Self, Related, First, Last, Prev, Next]] = Format[FullLinks[Self, Related, First, Last, Prev, Next]](
    (
      implicitly[TypeFormatter[Self]].readAt(__ \ "self") and
      implicitly[TypeFormatter[Related]].readAt(__ \ "related") and
      implicitly[TypeFormatter[First]].readAt(__ \ "first") and
      implicitly[TypeFormatter[Last]].readAt(__ \ "last") and
      implicitly[TypeFormatter[Prev]].readAt(__ \ "prev") and
      implicitly[TypeFormatter[Next]].readAt  (__ \ "next")
    )(FullLinks.apply[Self, Related, First, Last, Prev, Next] _),
    (
      implicitly[TypeFormatter[Self]].writeAt(__ \ "self") and
      implicitly[TypeFormatter[Related]].writeAt(__ \ "related") and
      implicitly[TypeFormatter[First]].writeAt(__ \ "first") and
      implicitly[TypeFormatter[Last]].writeAt(__ \ "last") and
      implicitly[TypeFormatter[Prev]].writeAt(__ \ "prev") and
      implicitly[TypeFormatter[Next]].writeAt  (__ \ "next")
    )(unlift(FullLinks.unapply[Self, Related, First, Last, Prev, Next]))
  )
}
