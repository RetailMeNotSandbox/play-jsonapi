package com.rmn.jsonapi

import play.api.libs.json.{Format, JsResult, Reads, Writes}

object ReadRefine {
  def apply[From, To <: From](refine: (From) => JsResult[To])(implicit format: Format[From]): Format[To] = Format[To](
    Reads(json => format.reads(json).flatMap(refine)),
    Writes(format.writes)
  )
}
