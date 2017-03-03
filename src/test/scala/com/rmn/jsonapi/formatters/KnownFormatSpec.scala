package com.rmn.jsonapi.formatters

import com.rmn.jsonapi.Container
import com.rmn.jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}

class KnownFormatSpec extends Specification {
  case class TestModel(foo: String, bar: String)
  implicit val testModelFormat = Json.format[TestModel]

  "KnownFormat" should {
    "error when reading a javascript undefined" in {
      implicit val containerFmt = Container.format(new KnownFormat[TestModel])

      val json = "{}"
      val validated = Json.parse(json).validate[Container[TestModel]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "error when reading a javascript null" in {
      implicit val containerFmt = Container.format(new KnownFormat[TestModel])

      val json = """{"item": null}"""
      val validated = Json.parse(json).validate[Container[TestModel]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "error when reading a wrong javascript value" in {
      implicit val containerFmt = Container.format(new KnownFormat[TestModel])

      val json = """{"item": 1234}"""
      val validated = Json.parse(json).validate[Container[TestModel]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "read a correct javascript value" in {
      implicit val containerFmt = Container.format(new KnownFormat[TestModel])

      val json = """{"item": {"foo":"fizz", "bar":"buzz"}}"""
      val validated = Json.parse(json).validate[Container[TestModel]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual TestModel("fizz", "buzz")
      }
    }

    "write a value" in {
      implicit val containerFmt = Container.format(new KnownFormat[TestModel])

      val container = Container(TestModel("fizz", "buzz"))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj("item" -> Json.obj("foo" -> "fizz", "bar" -> "buzz"))
      }
    }
  }
}
