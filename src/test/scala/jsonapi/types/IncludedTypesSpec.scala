package jsonapi.types

import jsonapi.models.TypeAliases.GenericResource
import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class IncludedTypesSpec extends Specification {
  "IncludedTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[IncludedTypes[toTest]])

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
      type toTest = Seq[GenericResource]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[IncludedTypes[toTest]])

      val json =
        """
          |{
          |  "item": [{
          |    "type": "foo",
          |    "id": "bar"
          |  }]
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Seq(GenericResource("foo", "bar"))
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[Seq[GenericResource]]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[IncludedTypes[toTest]])

      val json =
        """
          |{
          |  "item": [{
          |    "type": "foo",
          |    "id": "bar"
          |  }]
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(Seq(GenericResource("foo", "bar")))
      }
    }
  }
}
