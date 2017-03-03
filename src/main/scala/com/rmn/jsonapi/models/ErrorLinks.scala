package com.rmn.jsonapi.models

import com.rmn.jsonapi.formatters.TypeFormatter
import com.rmn.jsonapi.types.LinkTypes
import play.api.libs.json._

case class ErrorLinks[About : LinkTypes](about: About)
object ErrorLinks {
  implicit def formats[About : LinkTypes] : Format[ErrorLinks[About]] = Format[ErrorLinks[About]](
    implicitly[TypeFormatter[About]].readAt(__ \ "about").map(ErrorLinks.apply[About]),
    Writes(el => implicitly[TypeFormatter[About]].writeAt(__ \ "about").writes(el.about))
  )
}
