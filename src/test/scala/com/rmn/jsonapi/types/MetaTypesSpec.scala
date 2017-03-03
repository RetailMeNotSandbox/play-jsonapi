package com.rmn.jsonapi.types

import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsObject, JsSuccess, Json}

class MetaTypesSpec extends Specification {
  case class MyMeta(foo: String)
  implicit val myMetaFmt = Json.format[MyMeta]

  "MetaTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[MetaTypes[toTest]])

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
      implicit val containerFmt = Container.format(implicitly[MetaTypes[toTest]])

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
      implicit val containerFmt = Container.format(implicitly[MetaTypes[toTest]])

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
      type toTest = MyMeta
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[MetaTypes[toTest]])

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
        case JsSuccess(container, _) => container.item mustEqual MyMeta("bar")
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[MyMeta]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[MetaTypes[toTest]])

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
        case JsSuccess(container, _) => container.item mustEqual Some(MyMeta("bar"))
      }
    }
  }
}
