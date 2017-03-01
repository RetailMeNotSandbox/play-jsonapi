package jsonapi.models

import play.api.libs.json._

import scala.collection.Seq

object TypeAliases {
  type GenericMetaType = Option[JsObject]
  type GenericMaybeStringType = Option[String]
  type GenericAttributeType = Option[JsObject]
  type GenericResourceLinkageType = Option[Either[ResourceIdentifier, Seq[ResourceIdentifier]]]
  type GenericJsonApiVersion = JsonApiVersion[GenericMaybeStringType, GenericMetaType]
  type GenericJsonApiType = Option[GenericJsonApiVersion]
  type GenericLinkObject = LinkObject[GenericMetaType]
  def GenericLinkObject(href: String, meta: GenericMetaType = None) = new GenericLinkObject(href, meta)
  type GenericLinkType = Option[Either[String, GenericLinkObject]]
  type GenericFullLinks = FullLinks[GenericLinkType, GenericLinkType, GenericLinkType, GenericLinkType, GenericLinkType, GenericLinkType]
  type GenericFullLinkType = Option[GenericFullLinks]
  type GenericRelationshipObject = RelationshipObject[GenericFullLinkType, GenericResourceLinkageType, GenericMetaType]
  def GenericRelationshipObject(links: GenericFullLinkType = None, data: GenericResourceLinkageType = None, meta: GenericMetaType = None) = new GenericRelationshipObject(links, data, meta)
  type GenericRelationshipType = Option[Map[String, GenericRelationshipObject]]
  type GenericResourceLinks = ResourceLinks[GenericLinkType]
  def GenericResourceLinks(self: Option[String]) = new GenericResourceLinks(self.map(Left(_)))
  type GenericResourceLinksType = Option[GenericResourceLinks]
  type GenericResource = Resource[GenericAttributeType, GenericRelationshipType, GenericResourceLinksType, GenericMetaType]
  def GenericResource(`type`: String, id: String, attr: GenericAttributeType = None, rels: GenericRelationshipType = None, links: GenericResourceLinksType = None, meta: GenericMetaType = None) = new GenericResource(`type`, id, attr, rels, links, meta)
  type GenericDataType = Option[Either[Either[GenericResource, ResourceIdentifier], Either[Seq[GenericResource], Seq[ResourceIdentifier]]]]
  type GenericIncludedType = Option[Seq[GenericResource]]
  type GenericTopLevelData = TopLevelData[GenericJsonApiType, GenericDataType, GenericMetaType, GenericFullLinkType, GenericIncludedType]
  type GenericErrorSource = ErrorSource[GenericMaybeStringType, GenericMaybeStringType]
  def GenericErrorSource(pointer: GenericMaybeStringType = None, parameter: GenericMaybeStringType = None) = new GenericErrorSource(pointer, parameter)
  type GenericErrorSourceType = Option[GenericErrorSource]
  type GenericErrorLinks = ErrorLinks[GenericLinkType]
  def GenericErrorLinks(about: Option[String] = None) = new GenericErrorLinks(about.map(Left(_)))
  type GenericErrorLinkType = Option[GenericErrorLinks]
  type GenericErrorObject = ErrorObject[GenericMaybeStringType, GenericErrorLinkType, GenericMaybeStringType, GenericMaybeStringType, GenericMaybeStringType, GenericMaybeStringType, GenericErrorSourceType, GenericMetaType]
  def GenericErrorObject(id: GenericMaybeStringType = None, links: GenericErrorLinkType = None, status: GenericMaybeStringType = None, code: GenericMaybeStringType = None, title: GenericMaybeStringType = None, detail: GenericMaybeStringType = None, source: GenericErrorSourceType = None, meta: GenericMetaType = None) = new GenericErrorObject(id, links, status, code, title, detail, source, meta)
  type GenericTopLevelError = TopLevelError[GenericJsonApiType, Seq[GenericErrorObject], GenericMetaType]
}
