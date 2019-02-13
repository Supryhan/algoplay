name := "algoplay"

version := "0.1"

import sbt._
resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.8"

val libraryVersion = "1.5.0"

//libraryDependencies ++= Seq(
//  "com.github.julien-truffaut" %% "monocle-core" % "1.6.0-M1",
//  "com.github.julien-truffaut" %% "monocle-macro" % "1.6.0-M1",
//  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
//  "com.github.julien-truffaut" %% "monocle-generic" % "1.5.0",
//  "com.github.julien-truffaut" %% "monocle-refined" % "1.6.0-M1",
//  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
//  "com.github.julien-truffaut" %% "monocle-law" % "1.6.0-M1" % Test
//)

libraryDependencies ++= Seq(
  "com.github.julien-truffaut"  %%  "monocle-core"    % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-generic" % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-macro"   % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-state"   % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-refined" % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-law"     % libraryVersion % Test,
  "org.scalacheck"              %%  "scalacheck"      % "1.14.0"       % Test,
  "org.scalatest"               %%  "scalatest"       % "3.0.5"        % Test,
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
)

// for @Lenses macro support
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)