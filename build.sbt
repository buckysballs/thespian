lazy val appSettings = Seq(
  name := "thespian",
  scalaVersion := "2.11.8",
  version := "0.0.1"
)

lazy val akkaVersion = "2.4.2"

lazy val scalaTestVersion = "2.2.6"

lazy val sprayVersion = "1.3.1"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "spray nightlies" at "http://nightlies.spray.io"

lazy val lib = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion
    exclude ("org.scala-lang" , "scala-library"),
//  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
//    exclude ("org.slf4j", "slf4j-api")
//    exclude ("org.scala-lang" , "scala-library"),
//  "ch.qos.logback" % "logback-classic" % "1.0.13",
//  "io.spray" % "spray-can" % sprayVersion,
//  "io.spray" % "spray-routing" % sprayVersion,
//  "io.spray" % "spray-testkit" % sprayVersion % "test",
  "org.twitter4j" % "twitter4j-stream" % "3.0.3"
)

lazy val testLib = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test withSources()
)

lazy val thespian = (project in file("."))
  .settings(appSettings: _*)
  .settings(
    libraryDependencies ++= lib,
    libraryDependencies ++= testLib
  )