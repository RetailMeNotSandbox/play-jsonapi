package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.formatters.{KnownFormat, OptionLiftFormat, UndefinedFormatter, TypeFormatter}

import scala.annotation.implicitNotFound

@implicitNotFound(
  "This type is limited to String and Option[String].  ${T} was neither."
)
trait MaybeString[T] extends TypeFormatter[T]

object MaybeString {
  implicit val none = new UndefinedFormatter with MaybeString[Undefined]

  implicit val definiteString = new KnownFormat[String] with MaybeString[String]
  implicit val maybeString = new OptionLiftFormat[String] with MaybeString[Option[String]]

}
