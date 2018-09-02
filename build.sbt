name := "LiveRates"

version := "0.1"

scalaVersion := "2.12.6"

lazy val akkaVersion = "2.5.16"
lazy val akkaHttpVersion = "10.1.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
"org.scalatest" %% "scalatest" % "3.0.5" % "test"
)