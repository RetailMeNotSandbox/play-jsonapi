package jsonapi.types

import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsObject, JsSuccess, Json}

class AttributeTypesSpec extends Specification {
  case class MyAttributes(foo: String)
  implicit val myAttrFormat = Json.format[MyAttributes]

  "AttributeTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[AttributeTypes[toTest]])

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
    "allow a raw implementation" in {
      type toTest = JsObject
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[AttributeTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "foo": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Json.obj("foo" -> "bar")
      }
    }
    "allow an optional raw implementation" in {
      type toTest = Option[JsObject]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[AttributeTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "foo": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(Json.obj("foo" -> "bar"))
      }
    }

    "allow a concrete implementation" in {
      type toTest = MyAttributes
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[AttributeTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "foo": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual MyAttributes("bar")
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[MyAttributes]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[AttributeTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "foo": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(MyAttributes("bar"))
      }
    }
  }
}
