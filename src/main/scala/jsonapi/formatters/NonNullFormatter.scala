package jsonapi.formatters

import play.api.libs.json.{JsPath, OWrites, Reads}

trait NonNullFormatter[T] extends TypeFormatter[T] {
  def readAt(path: JsPath) : Reads[T] = path.read[T](this)
  def writeAt(path: JsPath) : OWrites[T] = path.write[T](this)
}
