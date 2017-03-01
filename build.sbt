name := "JsonAPI"

organization := "com.rmn.api"

version := "0.1"

scalaVersion := "2.11.6"

publishMavenStyle := true

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
  "com.github.tminglei" %% "slick-pg" % "0.14.4",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.14.4",
  "com.github.tminglei" %% "slick-pg_joda-time" % "0.14.4",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.0",
  "com.github.mauricio" %% "postgresql-async" % "0.2.21",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.6.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "nl.grons" %% "metrics-scala" % "3.5.4_a2.3",
  "io.dropwizard.metrics" % "metrics-json" % "3.1.2",
  "io.dropwizard.metrics" % "metrics-jvm" % "3.1.2",
  "com.miguno.akka" % "akka-mock-scheduler_2.11" % "0.4.0" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.14" % "test",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.14" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  specs2 % Test
)
