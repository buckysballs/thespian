lazy val appSettings = Seq(
  name := "thespian",
  scalaVersion := "2.11.8",
  version := "0.0.1"
)

lazy val akkaVersion = "2.4.4"

lazy val scalaTestVersion = "2.2.6"

lazy val lib = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
)

lazy val testLib = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test withSources(),
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion
)

lazy val thespian = (project in file("."))
  .settings(appSettings: _*)
  .settings(
    libraryDependencies ++= lib,
    libraryDependencies ++= testLib
  )