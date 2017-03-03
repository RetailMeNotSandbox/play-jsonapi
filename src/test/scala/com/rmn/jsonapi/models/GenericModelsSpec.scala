package com.rmn.jsonapi.models

import org.specs2.mutable.Specification
import play.api.libs.json._

class GenericModelsSpec extends Specification {
  import TypeAliases._

  "GenericModels" should {

    "autogenerate formats" in {
      // test that these compile
      val a = implicitly[Format[GenericLinkObject]]
      val b = implicitly[Format[GenericRelationshipObject]]
      val c = implicitly[Format[GenericResourceLinks]]
      val d = implicitly[Format[GenericResource]]
      val e = implicitly[Format[GenericJsonApiVersion]]
      val f = implicitly[Format[GenericFullLinks]]
      val g = implicitly[Format[ResourceIdentifier]]
      val h = implicitly[Format[GenericTopLevelData]]
      val i = implicitly[Format[GenericErrorSource]]
      val j = implicitly[Format[GenericErrorLinks]]
      val k = implicitly[Format[GenericErrorObject]]
      val l = implicitly[Format[GenericTopLevelError]]

      true mustEqual true
    }
  }
}
