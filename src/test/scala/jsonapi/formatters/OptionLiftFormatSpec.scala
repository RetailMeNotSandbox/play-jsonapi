package jsonapi.formatters

import jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}

class OptionLiftFormatSpec extends Specification {
  case class TestModel(foo: String, bar: String)
  val testModelFormat = Json.format[TestModel]
  implicit val knownFormat = new KnownFormat[TestModel]()(testModelFormat)

  "OptionLiftFormat" should {
    "read a javascript undefined" in {
      implicit val containerFmt = Container.format(new OptionLiftFormat[TestModel])

      val json = "{}"
      val validated = Json.parse(json).validate[Container[Option[TestModel]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual None
      }
    }

    "read a javascript null" in {
      implicit val containerFmt = Container.format(new OptionLiftFormat[TestModel])

      val json = """{"item": null}"""
      val validated = Json.parse(json).validate[Container[Option[TestModel]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual None
      }
    }

    "error when reading a wrong javascript value" in {
      implicit val containerFmt = Container.format(new OptionLiftFormat[TestModel])

      val json = """{"item": 1234}"""
      val validated = Json.parse(json).validate[Container[Option[TestModel]]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "read a correct javascript value" in {
      implicit val containerFmt = Container.format(new OptionLiftFormat[TestModel])

      val json = """{"item": {"foo":"fizz", "bar":"buzz"}}"""
      val validated = Json.parse(json).validate[Container[Option[TestModel]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(TestModel("fizz", "buzz"))
      }
    }

    "write a value" in {
      implicit val containerFmt = Container.format(new OptionLiftFormat[TestModel])

      val container = Container[Option[TestModel]](Some(TestModel("fizz", "buzz")))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj("item" -> Json.obj("foo" -> "fizz", "bar" -> "buzz"))
      }
    }

    "write a missing value" in {
      implicit val containerFmt = Container.format(new OptionLiftFormat[TestModel])

      val container = Container[Option[TestModel]](None)
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj()
      }
    }
  }
}
