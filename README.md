Play JsonAPI
============

Play JsonAPI is a helper library for representing the super flexible
[JsonAPI](http://jsonapi.org/) entities with the precise types for your use case.
For example, instead of using `Option[Either[ResourceIdentifier, Seq[ResourceIdentifier]]]`
to represent all the legal possibilities for resource linkages, you can refine it down to
`Seq[ResourceIdentifier]`.  

Play JsonAPI comes in at compile time, validates that your types are still valid
JsonAPI models, and then automatically generates [Play JSON](https://www.playframework.com/documentation/2.6.x/ScalaJson)
Formatters for your model.

Top Level Models
-----------------

Play JsonAPI works by having highly generic case classes and pre-defined type 
classes/context bounds to represent the JsonAPI model.  For example, the `TopLevelData` class 
(used to represent the [root object](http://jsonapi.org/format/#document-top-level)
of a JsonAPI response) is simply defined as 

```scala
case class TopLevelData[JApi: JsonApiTypes, Data: PrimaryDataTypes, Meta : MetaTypes, Links: FullLinkTypes, Included: IncludedTypes](
  jsonapi: JApi,
  data: Data,
  meta: Meta,
  links: Links,
  included: Included
)
```

Every field is a generic type, but the context bounds on the generic type 
ensures that it is a valid JsonAPI type.  

You don't have to generate types for each field; oftentimes there are parts of
the spec or model that you just don't care about.  In these cases, Play JsonAPI
provides the `Undefined` type and value that will ignore that field when doing 
JSON (de)serialization.

Let's walk through changing around the `meta` object type.  It should give a
feel for how to use the library.

```scala
import com.rmn.jsonapi.models._
import com.rmn.jsonapi.models.TypeAliases._
import play.api.libs.json._

case class SeqMeta(seqNum: Long)
object SeqMeta {
  implicit val fmt = Json.format[SeqMeta]
}

type SeqTopLevel  = TopLevelData[GenericJsonApiType, GenericDataType, SeqMeta, GenericFullLinkType, GenericIncludedType]
```

Here we declare a top level document type that accepts almost any valid JsonAPI
document, with the exception that the `meta` field _must_ be present and _must_
be a json object with a numeric `seqNum` sequence field.

If instead we wanted to let the `meta` object maybe be present, we would change
the type alias to 

```scala
type SeqTopLevel  = TopLevelData[GenericJsonApiType, GenericDataType, Option[SeqMeta], GenericFullLinkType, GenericIncludedType]
```

and now the type system and json serialization will respect the `Option` type.

Play JsonAPI comes with a full definition of "Generic" types that match the 
full JsonAPI spec.  These can be found in the `com.rmn.jsonapi.models.TypeAliases`
object.

While convenient, they still can be a hassle to remember.  For parts of the 
model that you don't care about, you can use the `Undefined` type.  `Undefined`
will ignore JSON while deserializing, and generate no JSON while serializing.

```scala
import com.rmn.jsonapi.Undefined

type SeqTopLevel  = TopLevelData[Undefined, GenericDataType, Option[SeqMeta], Undefined, Undefined]

val topLevel = new SeqTopLevel(Undefined, Some(JsObject()), Some(SeqMeta(123)), Undefined, Undefined)
```


Let's say we wanted to change the primary payload of `SeqTopLevel` to a list of
a local class:

```scala
import com.rmn.jsonapi.ReadRefine

case class ValidationAttributes(mobile: Boolean, droid: Boolean, ios: Boolean, desktop: Boolean)

object ValidationAttributes {
  implicit val format = Json.format[ValidationAttributes]
}


class ValidationResource(id: String, validations: ValidationAttributes)
  extends Resource[ValidationAttributes, Undefined, Undefined, Undefined]("validation", id, validations, Undefined, Undefined, Undefined)
  
object ValidationResource {
  implicit val format: Format[ValidationResource] = ReadRefine { in: Resource[ValidationAttributes, Undefined, Undefined, Undefined] =>
    if (in.`type` == "validation") {
      JsSuccess(new ValidationResource(in.id, in.attributes))
    } else {
      JsError(s"Expected type 'validation', found '${in.`type`}'.")
    }
  }
}

type SeqTopLevel  = TopLevelData[Undefined, Seq[ValidationResource], Option[SeqMeta], Undefined, Undefined]
```

Now our JSON Formatter will require a list of `ValidationResource`.  One piece of interest here is the `ReadRefine` 
helper, which helps uplift from the more generic `Resource[...]` to our new `ValidationResource`.

Installing
----------

For scala 2.11.X and Play Json 2.5:

```
libraryDependencies +=   "com.rmn" %% "play-jsonapi" % "0.1"
```

Publishing 
----------
For testing locally

    sbt publish-local

For publishing to Maven, your credentials should be available at ~/.ivy2/.credentials

    realm=Sonatype Nexus Repository Manager
    host=oss.sonatype.org
    user=***
    password=***

Make a new non-SNAPSHOT commit, and tag it in git. With the above credentials 
stored:

    sbt publishSigned
    <verify the artifacts in Nexus>
    sbt sonatypeRelease
    
Update `build.sbt`'s version to the next -SNAPSHOT, and fix the above 
installation instructions.
