package jsonapi.formatters

import jsonapi.Container
import org.specs2.mutable.Specification
import play.api.libs.json._

class EitherLiftFormatSpec extends Specification {
  case class MyLeft(foo: String)
  val myLeftFmt = Json.format[MyLeft]
  implicit val myLeftFmtr = new KnownFormat[MyLeft]()(myLeftFmt)
  implicit val myLeftOptFmtr = new OptionLiftFormat[MyLeft]()(myLeftFmt)
  case class MyRight(fizz: String)
  val myRightFmt = Json.format[MyRight]
  implicit val myRightFmtr = new KnownFormat[MyRight]()(myRightFmt)
  implicit val myRightOptFmtr = new OptionLiftFormat[MyRight]()(myRightFmt)

  "EitherLiftFormat" should {
    "error on reading a javascript undefined if neither left nor right take an optional" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, MyRight])

      val json =
        """
        |{
        |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[MyLeft, MyRight]]]
      validated must beLike {
        case err : JsError => err.errors must not be empty
      }
    }
    "read a javascript undefined if left takes an optional" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[Option[MyLeft], MyRight])

      val json =
        """
          |{
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[Option[MyLeft], MyRight]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Left(None)
      }
    }
    "read a javascript undefined if right takes an optional" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, Option[MyRight]])

      val json =
        """
          |{
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[MyLeft, Option[MyRight]]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Right(None)
      }
    }
    "default to left when reading a javascript undefined and both left and right take an optional" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[Option[MyLeft], Option[MyRight]])

      val json =
        """
          |{
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[Option[MyLeft], Option[MyRight]]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Left(None)
      }
    }
    "read a left" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, MyRight])

      val json =
        """
          |{
          |  "item": {
          |    "foo": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[MyLeft, MyRight]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Left(MyLeft("bar"))
      }
    }
    "read a left even with an optional right" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, Option[MyRight]])

      val json =
        """
          |{
          |  "item": {
          |    "foo": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[MyLeft, Option[MyRight]]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Left(MyLeft("bar"))
      }
    }
    "read a right" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, MyRight])

      val json =
        """
          |{
          |  "item": {
          |    "fizz": "buzz"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[MyLeft, MyRight]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Right(MyRight("buzz"))
      }
    }
    "read a right even with an optional left" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[Option[MyLeft], MyRight])

      val json =
        """
          |{
          |  "item": {
          |    "fizz": "buzz"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[Option[MyLeft], MyRight]]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Right(MyRight("buzz"))
      }
    }

    "error on read if neither left nor right" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[Option[MyLeft], Option[MyRight]])

      val json =
        """
          |{
          |  "item": {
          |    "whirly": "gig"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[Either[Option[MyLeft], Option[MyRight]]]]
      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }

    "write a left value" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, MyRight])

      val container = Container[Either[MyLeft, MyRight]](Left(MyLeft("bar")))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj("item" -> Json.obj("foo" -> "bar"))
      }
    }
    "write a missing left value" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[Option[MyLeft], MyRight])

      val container = Container[Either[Option[MyLeft], MyRight]](Left(None))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj()
      }
    }
    "write a right value" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, MyRight])

      val container = Container[Either[MyLeft, MyRight]](Right(MyRight("buzz")))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj("item" -> Json.obj("fizz" -> "buzz"))
      }
    }
    "write a missing right value" in {
      implicit val containerFmt = Container.format(new EitherLiftFormat[MyLeft, Option[MyRight]])

      val container = Container[Either[MyLeft, Option[MyRight]]](Right(None))
      val json = Json.toJson(container)

      json must beLike {
        case obj: JsObject => obj mustEqual Json.obj()
      }
    }
  }
}
