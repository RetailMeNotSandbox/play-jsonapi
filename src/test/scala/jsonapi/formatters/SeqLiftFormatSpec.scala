package jsonapi.formatters

import jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json}

class SeqLiftFormatSpec extends Specification {
  case class TestModel(foo: String, bar: String)
  val testModelFormat = Json.format[TestModel]
  implicit val knownFormat = new KnownFormat[TestModel]()(testModelFormat)

  "SeqLiftFormat" should {
    "error when reading a javascript undefined" in {
      implicit val containerFmt = Container.format(new SeqLiftFormat[TestModel])

      val json = "{}"
      val validated = Json.parse(json).validate[Container[Seq[TestModel]]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "error when reading a javascript null" in {
      implicit val containerFmt = Container.format(new SeqLiftFormat[TestModel])

      val json = """{"item": null}"""
      val validated = Json.parse(json).validate[Container[Seq[TestModel]]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "error when reading a wrong javascript value" in {
      implicit val containerFmt = Container.format(new SeqLiftFormat[TestModel])

      val json = """{"item": {"foo":"fizz", "bar":"buzz"}}"""
      val validated = Json.parse(json).validate[Container[Seq[TestModel]]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "read a correct javascript value" in {
      implicit val containerFmt = Container.format(new SeqLiftFormat[TestModel])

      val json = """{"item": [{"foo":"fizz", "bar":"buzz"}, {"foo": "hello", "bar": "world"}]}"""
      val validated = Json.parse(json).validate[Container[Seq[TestModel]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Seq(TestModel("fizz", "buzz"), TestModel("hello", "world"))
      }
    }

    "write a value" in {
      implicit val containerFmt = Container.format(new SeqLiftFormat[TestModel])

      val container = Container[Seq[TestModel]](Seq(TestModel("fizz", "buzz")))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj("item" -> Json.arr(Json.obj("foo" -> "fizz", "bar" -> "buzz")))
      }
    }

    "write an empty value" in {
      implicit val containerFmt = Container.format(new SeqLiftFormat[TestModel])

      val container = Container[Seq[TestModel]](Seq.empty)
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj("item" -> Json.arr())
      }
    }
  }
}
