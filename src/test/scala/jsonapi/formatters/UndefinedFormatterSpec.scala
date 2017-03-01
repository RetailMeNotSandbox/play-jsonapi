package jsonapi.formatters

import jsonapi.{Container, Undefined}
import org.specs2.mutable.Specification
import play.api.libs.json.{JsObject, JsSuccess, Json}

class UndefinedFormatterSpec extends Specification {
  "UndefinedFormatter" should {
    "read a javascript undefined" in {
      implicit val containerFmt = Container.format(new UndefinedFormatter)

      val json = "{}"
      val validated = Json.parse(json).validate[Container[Undefined]]
      validated must beLike {
        case JsSuccess(Container(item), _) => item mustEqual Undefined
      }
    }

    "read a javascript null" in {
      implicit val containerFmt = Container.format(new UndefinedFormatter)

      val json = """{"item": null}"""
      val validated = Json.parse(json).validate[Container[Undefined]]
      validated must beLike {
        case JsSuccess(Container(item), _) => item mustEqual Undefined
      }
    }

    "ignore reading a javascript value" in {
      implicit val containerFmt = Container.format(new UndefinedFormatter)

      val json = """{"item": 1234}"""
      val validated = Json.parse(json).validate[Container[Undefined]]
      validated must beLike {
        case JsSuccess(Container(item), _) => item mustEqual Undefined
      }
    }

    "not write a value" in {
      implicit val containerFmt = Container.format(new UndefinedFormatter)

      val container = Container(Undefined)
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj.value must not haveKey("item")
      }
    }
  }
}
