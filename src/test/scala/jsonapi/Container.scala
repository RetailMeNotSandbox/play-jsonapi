package jsonapi

import jsonapi.formatters.TypeFormatter
import play.api.libs.json._

case class Container[T](item: T)

object Container {
  def format[T](formatter: TypeFormatter[T]): Format[Container[T]] = Format[Container[T]](
    formatter.readAt(__ \ "item").map(Container.apply[T]),
    Writes(container => formatter.writeAt(__ \ "item").writes(container.item))
  )
}
