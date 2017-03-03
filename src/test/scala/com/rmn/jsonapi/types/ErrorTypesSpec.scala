package com.rmn.jsonapi.types

import com.rmn.jsonapi.models.TypeAliases.GenericErrorObject
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class ErrorTypesSpec extends Specification {
  "ErrorTypes" should {
    "allow a concrete seq implementation" in {
      type toTest = GenericErrorObject
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ErrorTypes[Seq[toTest]]])

      val json =
        """
          |{
          |  "item": []
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Seq[toTest]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Seq.empty
      }
    }
  }
}
