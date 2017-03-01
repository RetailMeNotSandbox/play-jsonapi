package jsonapi.types

import jsonapi.models.LinkObject
import jsonapi.models.TypeAliases.GenericFullLinks
import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class FullLinkTypesSpec extends Specification {
  "FullLinkTypes" should {


    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[FullLinkTypes[toTest]])

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
      type toTest = GenericFullLinks
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[FullLinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "self": "http://a.com",
          |    "related": {
          |      "href": "http://b.com",
          |      "meta": {
          |        "foo": "bar"
          |      }
          |    },
          |    "first": "http://c.com",
          |    "prev": "http://e.com",
          |    "next": "http://f.com"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual new GenericFullLinks(
          Some(Left("http://a.com")),
          Some(Right(LinkObject("http://b.com", Some(Json.obj("foo" -> "bar"))))),
          Some(Left("http://c.com")),
          None,
          Some(Left("http://e.com")),
          Some(Left("http://f.com"))
        )
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[GenericFullLinks]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[FullLinkTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "self": "http://a.com",
          |    "related": {
          |      "href": "http://b.com",
          |      "meta": {
          |        "foo": "bar"
          |      }
          |    },
          |    "first": "http://c.com",
          |    "prev": "http://e.com",
          |    "next": "http://f.com"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(new GenericFullLinks(
          Some(Left("http://a.com")),
          Some(Right(LinkObject("http://b.com", Some(Json.obj("foo" -> "bar"))))),
          Some(Left("http://c.com")),
          None,
          Some(Left("http://e.com")),
          Some(Left("http://f.com"))
        ))
      }
    }
  }
}
