package jsonapi

import jsonapi.models.Resource
import org.specs2.mutable.Specification
import play.api.libs.json.{Format, JsError, JsSuccess, Json}

class ReadRefineSpec extends Specification {
  type ChangefeedParent = Resource[Undefined, Undefined, Undefined, Undefined]
  class ChangefeedResource(id: String) extends ChangefeedParent("changefeed", id, Undefined, Undefined, Undefined, Undefined)

  object ChangefeedResource {
    implicit val format : Format[ChangefeedResource] = ReadRefine { in : ChangefeedParent =>
      if (in.`type` != "changefeed") {
        JsError(s"Expected type 'changefeed', found '${in.`type`}'.")
      } else {
        JsSuccess(new ChangefeedResource(in.id))
      }
    }
  }

  "ReadRefine" should {
    "read apply refinement validation" in {
      val json =
        """
          |{
          |  "type": "almostchangefeed",
          |  "id": "primary"
          |}
        """.stripMargin


      val validated = Json.parse(json).validate[ChangefeedResource]

      validated must beLike {
        case err: JsError => err.errors must not be empty
      }
    }
    "read the refined type" in {
      val json =
        """
          |{
          |  "type": "changefeed",
          |  "id": "primary"
          |}
        """.stripMargin


      val validated = Json.parse(json).validate[ChangefeedResource]

      validated must beLike {
        case JsSuccess(item, _) => item mustEqual new ChangefeedResource("primary")
      }
    }
  }
}
