name := "Play-JsonAPI"

organization := "com.rmn"

organizationName := "RetailMeNot"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-json_2.11" % "2.5.12",
  "org.specs2" %% "specs2-core" % "2.4.17" % "test"
)

licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/RetailMeNotSandbox/play-jsonapi"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/RetailMeNotSandbox/play-jsonapi"),
    "scm:git@github.com:RetailMeNotSandbox/play-jsonapi.git"
  )
)

developers := List(
  Developer(
    id    = "talton",
    name  = "Thomas Alton",
    email = "talton@rmn.com",
    url   = url("https://github.com/moatra")
  )
)

// http://www.scala-sbt.org/release/docs/Using-Sonatype.html

pomIncludeRepository := { _ => false }

publishArtifact in Test := false

publishMavenStyle := true

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}
