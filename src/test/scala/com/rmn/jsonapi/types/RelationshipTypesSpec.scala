package com.rmn.jsonapi.types

import com.rmn.jsonapi.models.ResourceIdentifier
import com.rmn.jsonapi.models.TypeAliases.GenericRelationshipObject
import com.rmn.jsonapi.Undefined
import com.rmn.jsonapi.Container
import com.rmn.jsonapi.models.ResourceIdentifier
import org.specs2.mutable.Specification
import play.api.libs.json.{JsSuccess, Json}

class RelationshipTypesSpec extends Specification {
  case class MyRelationships(parent: Option[GenericRelationshipObject])
  implicit val myRelationshipFormat = Json.format[MyRelationships]

  "RelationshipTypes" should {
    "allow undefined" in {
      type toTest = Undefined
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[RelationshipTypes[toTest]])

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
      type toTest = Map[String, GenericRelationshipObject]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[RelationshipTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "parent": {
          |      "data": {"type": "foo", "id": "bar"}
          |    }
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Map("parent" -> GenericRelationshipObject(data=Some(Left(ResourceIdentifier("foo", "bar")))))
      }
    }
    "allow an optional raw implementation" in {
      type toTest = Option[Map[String, GenericRelationshipObject]]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[RelationshipTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "parent": {
          |      "data": {"type": "foo", "id": "bar"}
          |    }
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(Map("parent" -> GenericRelationshipObject(data=Some(Left(ResourceIdentifier("foo", "bar"))))))
      }
    }

    "allow a concrete implementation" in {
      type toTest = MyRelationships
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[RelationshipTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "parent": {
          |      "data": {"type": "foo", "id": "bar"}
          |    }
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual MyRelationships(Some(GenericRelationshipObject(data=Some(Left(ResourceIdentifier("foo", "bar"))))))
      }
    }

    "allow an optional concrete implementation" in {
      type toTest = Option[MyRelationships]
      // the test in the next line is simply whether or not it compiles
      implicit val containerFmt = Container.format(implicitly[RelationshipTypes[toTest]])

      val json =
        """
          |{
          |  "item": {
          |    "parent": {
          |      "data": {"type": "foo", "id": "bar"}
          |    }
          |  }
          |}
        """.stripMargin

      val validated = Json.parse(json).validate[Container[toTest]]
      validated must beLike {
        case JsSuccess(container, _) => container.item mustEqual Some(MyRelationships(Some(GenericRelationshipObject(data=Some(Left(ResourceIdentifier("foo", "bar")))))))
      }
    }
  }
}
