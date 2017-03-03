package com.rmn.jsonapi.formatters

import play.api.libs.json._

class OptionLiftFormat[T](implicit format: Format[T]) extends TypeFormatter[Option[T]] {
  def writes(item: Option[T]) : JsValue = item.map(format.writes).getOrElse(JsNull)
  def reads(value: JsValue) : JsResult[Option[T]] = value.validateOpt(format)
  def readAt(path: JsPath) : Reads[Option[T]] = path.readNullable[T](format)
  def writeAt(path: JsPath) : OWrites[Option[T]] = path.writeNullable[T](format)
}
