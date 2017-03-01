package jsonapi.models

import jsonapi.formatters.TypeFormatter
import jsonapi.types.MaybeString
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ErrorSource[Pointer: MaybeString, Param: MaybeString](pointer: Pointer, parameter: Param)

object ErrorSource {
  implicit def formats[Pointer: MaybeString, Param : MaybeString] : Format[ErrorSource[Pointer, Param]] = Format[ErrorSource[Pointer, Param]](
    (
      implicitly[TypeFormatter[Pointer]].readAt(__ \ "pointer") and
      implicitly[TypeFormatter[Param]].readAt(__ \ "parameter")
    )(ErrorSource.apply[Pointer, Param] _),
    (
      implicitly[TypeFormatter[Pointer]].writeAt(__ \ "pointer") and
      implicitly[TypeFormatter[Param]].writeAt(__ \ "parameter")
    )(unlift(ErrorSource.unapply[Pointer, Param]))
  )
}
