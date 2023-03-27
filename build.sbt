scalaVersion := "2.13.10"


name := "scala-dev-mooc-2022-11"
organization := "ru.otus"
version := "1.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.4.8",
  "co.fs2" %% "fs2-core" % "3.6.1",
  "io.circe" %% "circe-generic" % "0.14.5",
  "org.http4s" %% "http4s-blaze-server" % "0.23.14",
  "org.http4s" %% "http4s-circe" % "0.23.18",
  "org.http4s" %% "http4s-dsl" % "0.23.18",
  "org.http4s" %% "http4s-blaze-client" % "0.23.14",
  "ch.qos.logback" % "logback-classic" % "1.4.6",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "org.specs2" %% "specs2-core" % "4.19.2"
)


scalacOptions ++= Seq(
  "-Ymacro-annotations"
)


/*

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.9.0"

val http4sVersion = "0.23.18"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "blaze-core"          % http4sVersion,
  "org.http4s" %% "http4s-circe"        % http4sVersion
)
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-core"         % http4sVersion,
  "org.http4s" %% "http4s-client"       % http4sVersion,
  "org.http4s" %% "http4s-server"       % http4sVersion,
)

val circeVersion = "0.14.1"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion ,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)


*/



