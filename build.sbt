name := "algoplay"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core" % "1.6.0-M1",
  "com.github.julien-truffaut" %% "monocle-macro" % "1.6.0-M1",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
  "com.github.julien-truffaut" %% "monocle-generic" % "1.5.0",
  "com.github.julien-truffaut" %% "monocle-refined" % "1.6.0-M1",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.github.julien-truffaut" %% "monocle-law" % "1.6.0-M1" % Test
)
