package com.rmn.jsonapi.formatters

import play.api.libs.json.{Format, JsResult, JsValue}

class KnownFormat[T](implicit fmt: Format[T]) extends NonNullFormatter[T] {
  def writes(item: T): JsValue = fmt.writes(item)
  def reads(value: JsValue): JsResult[T] = fmt.reads(value)
}
