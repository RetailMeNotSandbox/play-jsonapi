package jsonapi.models

import jsonapi.formatters.TypeFormatter
import jsonapi.types._
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Resource[Attr: AttributeTypes, Rels: RelationshipTypes, Links: ResourceLinkTypes, Meta: MetaTypes](
                                                                                                               `type`: String,
                                                                                                               id: String,
                                                                                                               attributes: Attr,
                                                                                                               relationships: Rels,
                                                                                                               links: Links,
                                                                                                               meta: Meta
                                                                                                             )

object AdhocValidate {
  def apply(validate: JsValue => Iterable[JsError]): Reads[JsValue] = {
    Reads[JsValue] { json =>
      val errs = validate(json)
      if (errs.isEmpty) {
        JsSuccess(json)
      } else {
        errs.reduce(_ ++ _)
      }
    }
  }
}

object Resource {
  implicit def formats[Attr: AttributeTypes, Rels: RelationshipTypes, Links: ResourceLinkTypes, Meta: MetaTypes]: Format[Resource[Attr, Rels, Links, Meta]] = {
    val reserved = Set("")

    // http://jsonapi.org/format/#document-resource-object-fields
    def checkFields(attr: JsObject, rels: JsObject): Iterable[JsError] = {
      val counts = (Seq("id", "type") ++ attr.keys.toSeq ++ rels.keys.toSeq).groupBy(identity).mapValues(_.size)
      counts.filter { case (field, count) => count > 1 }.map { case (field, count) => JsError(s"error.jsonapi.field.duplicate('$field')") }
    }
    // http://jsonapi.org/format/#document-resource-object-attributes
    def checkReserved(attr: JsObject) : Iterable[JsError] = {
      val rels = if ((attr \\ "relationships").nonEmpty) {
        Seq(JsError(s"error.jsonapi.attributes.reserved-member('relationships')"))
      } else {
        Seq[JsError]()
      }

      val links = if ((attr \\ "links").nonEmpty) {
        Seq(JsError(s"error.jsonapi.attributes.reserved-member('links')"))
      } else {
        Seq[JsError]()
      }

      rels ++ links
    }

    Format[Resource[Attr, Rels, Links, Meta]](
      (
        AdhocValidate { json =>
          val root = json.asOpt[JsObject].getOrElse(Json.obj())
          val attrs = root.value.get("attributes").flatMap(_.asOpt[JsObject]).getOrElse(Json.obj())
          val rels = root.value.get("relationships").flatMap(_.asOpt[JsObject]).getOrElse(Json.obj())

          checkFields(attrs, rels) ++ checkReserved(attrs)
        } andKeep
          (__ \ "type").read[String] and
          (__ \ "id").read[String] and
          implicitly[TypeFormatter[Attr]].readAt(__ \ "attributes") and
          implicitly[TypeFormatter[Rels]].readAt(__ \ "relationships") and
          implicitly[TypeFormatter[Links]].readAt(__ \ "links") and
          implicitly[TypeFormatter[Meta]].readAt(__ \ "meta")
        ) (Resource.apply[Attr, Rels, Links, Meta] _),
      (
        (__ \ "type").write[String] and
          (__ \ "id").write[String] and
          implicitly[TypeFormatter[Attr]].writeAt(__ \ "attributes") and
          implicitly[TypeFormatter[Rels]].writeAt(__ \ "relationships") and
          implicitly[TypeFormatter[Links]].writeAt(__ \ "links") and
          implicitly[TypeFormatter[Meta]].writeAt(__ \ "meta")
        ) (unlift(Resource.unapply[Attr, Rels, Links, Meta]))
    )
  }
}
