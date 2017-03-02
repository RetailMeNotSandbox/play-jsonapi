name := "JsonAPI"

organization := "com.rmn.api"

version := "0.1"

scalaVersion := "2.11.6"

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := Some("Nexus Repository Manager OSS" at "https://maven.pkg.rmn.io/nexus/content/repositories/releases")

libraryDependencies ++= Seq(
  "com.typesafe.play" % "play-json_2.11" % "2.5.12",
  specs2 % Test
)
