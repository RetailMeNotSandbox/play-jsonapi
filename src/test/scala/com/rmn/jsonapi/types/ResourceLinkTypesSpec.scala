package com.rmn.jsonapi.types

import com.rmn.jsonapi.models.TypeAliases.GenericResourceLinks
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class ResourceLinkTypesSpec extends Specification {
  "ResourceLinkTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkTypes[toTest]])

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
      type toTest = GenericResourceLinks
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "self": "http://example.org"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual GenericResourceLinks(Some("http://example.org"))
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[GenericResourceLinks]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "self": "http://example.org"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(GenericResourceLinks(Some("http://example.org")))
      }
    }
  }
}
