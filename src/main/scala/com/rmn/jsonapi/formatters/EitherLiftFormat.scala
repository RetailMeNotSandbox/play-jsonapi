package com.rmn.jsonapi.formatters

import play.api.libs.json._

class EitherLiftFormat[L, R](implicit leftFormat: TypeFormatter[L], rightFormat: TypeFormatter[R]) extends TypeFormatter[Either[L, R]] {
  def writes(item: Either[L, R]) : JsValue = item.fold(leftFormat.writes, rightFormat.writes)
  def reads(value: JsValue) : JsResult[Either[L, R]]= leftFormat.reads(value).map[Either[L, R]](Left(_)).orElse(rightFormat.reads(value).map(Right(_)))

  def readAt(path: JsPath) : Reads[Either[L, R]] = leftFormat.readAt(path).map[Either[L, R]](Left(_)).orElse(rightFormat.readAt(path).map(Right(_)))
  def writeAt(path: JsPath) : OWrites[Either[L, R]] = OWrites(_.fold(leftFormat.writeAt(path).writes, rightFormat.writeAt(path).writes))
}
