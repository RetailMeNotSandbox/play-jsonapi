package jsonapi.models

import jsonapi.formatters.TypeFormatter
import jsonapi.types.LinkTypes
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ErrorLinks[About : LinkTypes](about: About)
object ErrorLinks {
  implicit def formats[About : LinkTypes] : Format[ErrorLinks[About]] = Format[ErrorLinks[About]](
    implicitly[TypeFormatter[About]].readAt(__ \ "about").map(ErrorLinks.apply[About]),
    Writes(el => implicitly[TypeFormatter[About]].writeAt(__ \ "about").writes(el.about))
  )
}
