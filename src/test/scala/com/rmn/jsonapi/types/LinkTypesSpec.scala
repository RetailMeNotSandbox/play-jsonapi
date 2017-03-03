package com.rmn.jsonapi.types

import com.rmn.jsonapi.models.TypeAliases.GenericLinkObject
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class LinkTypesSpec extends Specification {
  "LinkTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[LinkTypes[toTest]])

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
    "allow a string" in {
      type toTest = String
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[LinkTypes[toTest]])

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
      implicit val containerFmt = Container.format(implicitly[LinkTypes[toTest]])

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

    "allow a linkobject implementation" in {
      type toTest = GenericLinkObject
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[LinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "href": "http://example.org"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual GenericLinkObject("http://example.org")
      }
    }

    "allow an optional linkobject implementation" in {
      type toTest = Option[GenericLinkObject]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[LinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "href": "http://example.org"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(GenericLinkObject("http://example.org"))
      }
    }

    "allow either a string or a linkobject" in {
      type toTest = Either[String, Option[GenericLinkObject]]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[LinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "href": "http://example.org"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Right(Some(GenericLinkObject("http://example.org")))
      }
    }
  }
}
