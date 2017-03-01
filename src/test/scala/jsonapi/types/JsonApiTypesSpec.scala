package jsonapi.types

import jsonapi.models.JsonApiVersion
import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class JsonApiTypesSpec extends Specification {
  "JsonApiTypes" should {
    "allow missing/undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[JsonApiTypes[toTest]])

      val json = "{}"
      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Undefined
      }
    }

    "allow a concrete implementation" in {
      type toTest = JsonApiVersion[String, Undefined]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[JsonApiTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "version": "1.0"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual JsonApiVersion("1.0", Undefined)
      }
    }

    "allow an optional implementation" in {
      type toTest = Option[JsonApiVersion[String, Undefined]]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[JsonApiTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "version": "1.0"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(JsonApiVersion("1.0", Undefined))
      }
    }
  }
}
