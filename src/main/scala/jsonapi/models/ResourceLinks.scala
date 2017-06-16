package jsonapi.models

import jsonapi.formatters.TypeFormatter
import jsonapi.types.LinkTypes
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ResourceLinks[Self: LinkTypes](self: Self)

object ResourceLinks {
  implicit def formats[Self: LinkTypes] : Format[ResourceLinks[Self]] = Format[ResourceLinks[Self]](
    implicitly[TypeFormatter[Self]].readAt(__ \ "self").map(ResourceLinks.apply[Self]),
    Writes(rl => implicitly[TypeFormatter[Self]].writeAt(__ \ "self").writes(rl.self))
  )
}
