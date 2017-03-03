package com.rmn.jsonapi.types

import com.rmn.jsonapi.models.ResourceIdentifier
import com.rmn.jsonapi.models.TypeAliases.GenericResource
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import com.rmn.jsonapi.models.ResourceIdentifier
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class PrimaryDataTypesSpec extends Specification {
  "PrimaryDataTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

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
    "allow a single resource" in {
      type toTest = GenericResource
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "type": "foo",
          |    "id": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual GenericResource("foo", "bar")
      }
    }

    "allow a single optional resource" in {
      type toTest = Option[GenericResource]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "type": "foo",
          |    "id": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(GenericResource("foo", "bar"))
      }
    }

    "allow a single resourceidentifier" in {
      type toTest = ResourceIdentifier
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "type": "foo",
          |    "id": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual ResourceIdentifier("foo", "bar")
      }
    }

    "allow a single optional resourceidentifier" in {
      type toTest = Option[ResourceIdentifier]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "type": "foo",
          |    "id": "bar"
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(ResourceIdentifier("foo", "bar"))
      }
    }

    "allow a sequence of resources" in {
      type toTest = Seq[GenericResource]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

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

    "allow a sequence of resourceidentifiers" in {
      type toTest = Seq[ResourceIdentifier]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

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
        case JsSuccess(container, _) => container.item mustEqual Seq(ResourceIdentifier("foo", "bar"))
      }
    }

    "allow an either combination" in {
      type toTest = Either[Option[GenericResource], Seq[GenericResource]]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[PrimaryDataTypes[toTest]])

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
        case JsSuccess(container, _) => container.item mustEqual Right(Seq(GenericResource("foo", "bar")))
      }
    }
  }
}
