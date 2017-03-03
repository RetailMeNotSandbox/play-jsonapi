package com.rmn.jsonapi.formatters

import play.api.libs.json.{Format, JsPath, OWrites, Reads}

// TypeFormatter is a concept of an OReads (Object Reads) that is not present in play-json
// It allows the formatters to specify whether or not a given key is present in the object.
trait TypeFormatter[T] extends Format[T] {
  def readAt(path: JsPath) : Reads[T]
  def writeAt(path: JsPath) : OWrites[T]
}
