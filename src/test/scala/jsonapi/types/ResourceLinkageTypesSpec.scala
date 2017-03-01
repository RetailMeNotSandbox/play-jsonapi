package jsonapi.types

import jsonapi.models.ResourceIdentifier
import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class ResourceLinkageTypesSpec extends Specification {
  "ResourceLinkageTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkageTypes[toTest]])

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
    "allow a single resource identifier" in {
      type toTest = ResourceIdentifier
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkageTypes[toTest]])

      val json =
        """
          |{
          |  "item": {"type": "foo", "id": "bar"}
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual ResourceIdentifier("foo", "bar")
      }
    }

    "allow an optional resource identifier" in {
      type toTest = Option[ResourceIdentifier]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkageTypes[toTest]])

      val json =
        """
          |{
          |  "item": {"type": "foo", "id": "bar"}
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(ResourceIdentifier("foo", "bar"))
      }
    }

    "allow a sequence of resource identifiers" in {
      type toTest = Seq[ResourceIdentifier]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkageTypes[toTest]])

      val json =
        """
          |{
          |  "item": [{"type": "foo", "id": "bar"}]
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Seq(ResourceIdentifier("foo", "bar"))
      }
    }

    "allow either a singular or sequence of resource identifiers" in {
      type toTest = Either[Option[ResourceIdentifier], Seq[ResourceIdentifier]]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[ResourceLinkageTypes[toTest]])

      val json =
        """
          |{
          |  "item": [{"type": "foo", "id": "bar"}]
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Right(Seq(ResourceIdentifier("foo", "bar")))
      }
    }
  }
}
