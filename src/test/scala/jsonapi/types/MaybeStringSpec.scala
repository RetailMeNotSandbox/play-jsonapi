package jsonapi.types

import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class MaybeStringSpec extends Specification {
  "MaybeString" should {
    "allow a string" in {
      type toTest = String
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[MaybeString[toTest]])

      val json =
        """
          |{
          |  "item": "http://example.org"
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual "http://example.org"
      }
    }

    "allow an optional string" in {
      type toTest = Option[String]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[MaybeString[toTest]])

      val json =
        """
          |{
          |  "item": "http://example.org"
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some("http://example.org")
      }
    }
  }
}
