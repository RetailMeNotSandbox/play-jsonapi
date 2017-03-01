package jsonapi.formatters

import play.api.libs.json._

import scala.collection.Seq


class SeqLiftFormat[T](implicit format: Format[T]) extends NonNullFormatter[Seq[T]] {
  def writes(items: Seq[T]) :JsValue = JsArray(items.map(format.writes))
  def reads(value: JsValue) : JsResult[Seq[T]] = value.validate[Seq[T]]
}
