package com.rmn.jsonapi.models

import play.api.libs.json.{Format, Json}

case class ResourceIdentifier(`type`: String, id: String)

object ResourceIdentifier {
  implicit val format : Format[ResourceIdentifier] = Json.format[ResourceIdentifier]
}
