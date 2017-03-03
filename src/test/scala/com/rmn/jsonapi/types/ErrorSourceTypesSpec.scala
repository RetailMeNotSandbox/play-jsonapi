package com.rmn.jsonapi.types

import com.rmn.jsonapi.models.TypeAliases.GenericErrorSource
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class ErrorSourceTypesSpec extends Specification {
  "ErrorSourceTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ErrorSourceTypes[toTest]])

      val json =
        """
          |{
          |
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Undefined
      }
    }
    "allow a concrete implementation" in {
      type toTest = GenericErrorSource
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ErrorSourceTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "pointer": "item/foo/bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual GenericErrorSource(Some("item/foo/bar"))
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[GenericErrorSource]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ErrorSourceTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "pointer": "item/foo/bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(GenericErrorSource(Some("item/foo/bar")))
      }
    }
  }
}
