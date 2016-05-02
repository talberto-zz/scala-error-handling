name := """scala-error-handling"""

version := "1.0-SNAPSHOT"

lazy val root = project in file(".")

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.play" % "play-json_2.11" % "2.5.3",
  "org.scalatest" %% "scalatest" % "2.2.6" % Test
)
