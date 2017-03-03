package com.rmn.jsonapi.formatters

import com.rmn.jsonapi.Undefined
import play.api.libs.json._

class UndefinedFormatter extends TypeFormatter[Undefined] {
  def reads(json: JsValue) : JsResult[Undefined] = JsSuccess(Undefined)
  def writes(item: Undefined) : JsValue = JsNull
  def readAt(path : JsPath) : Reads[Undefined] = path.readNullable[Undefined](this).map(_.getOrElse(Undefined))
  def writeAt(path: JsPath) : OWrites[Undefined] = OWrites[Undefined]{ _ => Json.obj()}
}
