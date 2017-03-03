package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types.{ErrorSourceTypes, MetaTypes, MaybeString, ErrorLinkTypes}
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ErrorObject[Id : MaybeString, Links : ErrorLinkTypes, Status : MaybeString, Code: MaybeString, Title : MaybeString, Detail: MaybeString, Source : ErrorSourceTypes, Meta : MetaTypes] (
  id: Id,
  links: Links,
  status: Status,
  code: Code,
  title: Title,
  detail: Detail,
  source: Source,
  meta: Meta
)
object ErrorObject {
  implicit def formats[Id : MaybeString, Links : ErrorLinkTypes, Status : MaybeString, Code: MaybeString, Title : MaybeString, Detail: MaybeString, Source : ErrorSourceTypes, Meta : MetaTypes] : Format[ErrorObject[Id, Links, Status, Code, Title, Detail, Source, Meta]] = Format[ErrorObject[Id, Links, Status, Code, Title, Detail, Source, Meta]](
    (
      implicitly[TypeFormatter[Id]].readAt(__ \ "id") and
      implicitly[TypeFormatter[Links]].readAt(__ \ "links") and
      implicitly[TypeFormatter[Status]].readAt(__ \ "status") and
      implicitly[TypeFormatter[Code]].readAt(__ \ "code") and
      implicitly[TypeFormatter[Title]].readAt(__ \ "title") and
      implicitly[TypeFormatter[Detail]].readAt(__ \ "detail") and
      implicitly[TypeFormatter[Source]].readAt(__ \ "source") and
      implicitly[TypeFormatter[Meta]].readAt(__ \ "meta")
    )(ErrorObject.apply[Id, Links, Status, Code, Title, Detail, Source, Meta] _),
    (
      implicitly[TypeFormatter[Id]].writeAt(__ \ "id") and
      implicitly[TypeFormatter[Links]].writeAt(__ \ "links") and
      implicitly[TypeFormatter[Status]].writeAt(__ \ "status") and
      implicitly[TypeFormatter[Code]].writeAt(__ \ "code") and
      implicitly[TypeFormatter[Title]].writeAt(__ \ "title") and
      implicitly[TypeFormatter[Detail]].writeAt(__ \ "detail") and
      implicitly[TypeFormatter[Source]].writeAt(__ \ "source") and
      implicitly[TypeFormatter[Meta]].writeAt(__ \ "meta")
    )(unlift(ErrorObject.unapply[Id, Links, Status, Code, Title, Detail, Source, Meta]))
  )
}
