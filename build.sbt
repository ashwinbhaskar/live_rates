name := "LiveRates"

version := "0.1"

scalaVersion := "2.12.6"

lazy val akkaVersion = "2.5.16"
lazy val akkaHttpVersion = "10.1.4"
lazy val akkaSprayVersion = "10.1.4"
lazy val akkaStreamVersion = "2.5.16"
lazy val scaladiVersion = "0.5.8"
lazy val redisClientVersion = "3.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaSprayVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
  "org.scaldi" %%  "scaldi" % scaladiVersion,
  "net.debasishg" %% "redisclient" % redisClientVersion,
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)