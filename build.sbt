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
    exclude("org.scala-lang", "scala-library"),
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.16" ,
  "com.typesafe.akka" %% "akka-stream" % "2.4.16",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.16",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.16",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.twitter" %% "algebird-core" % "0.12.3",
  "io.spray" %% "spray-http" % "1.3.1",
  "io.spray" %% "spray-can" % "1.3.1",
  "io.spray"                %% "spray-client"          % "1.3.1",
  "io.spray"                %% "spray-routing"         % "1.3.1",
  "io.spray"                %% "spray-json"         % "1.3.1",
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