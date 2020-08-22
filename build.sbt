name := "algoplay"

version := "0.1"

scalaVersion := "2.12.11"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

//addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.1.2")

//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

val libraryVersion = "1.5.0"
val circeVersion = "0.11.1"

libraryDependencies ++= Seq(
  "org.scalaz"                  %% "scalaz-zio"       % "0.9",
  "com.typesafe.akka"           %% "akka-actor"       % "2.5.21",
  "com.typesafe.akka"           %% "akka-kernel"      % "2.5-M1",
  "com.typesafe.akka"           %% "akka-stream"      % "2.5.21",
  "com.typesafe.akka"           %% "akka-slf4j"       % "2.5.21",
  "com.typesafe.akka"           %% "akka-testkit"     % "2.5.21" % Test,
  "com.github.julien-truffaut"  %%  "monocle-core"    % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-generic" % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-macro"   % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-state"   % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-refined" % libraryVersion,
  "com.github.julien-truffaut"  %%  "monocle-law"     % libraryVersion % Test,
  "org.scalacheck"              %%  "scalacheck"      % "1.14.0"       % Test,
  "org.scalatest"               %%  "scalatest"       % "3.2.0-SNAP4"  % Test,
  "org.scala-lang"              %   "scala-reflect"   % scalaVersion.value,
  "org.scala-lang"              %   "scala-compiler"  % scalaVersion.value      % "provided",
  "org.typelevel"               %%  "cats-core"       % "2.0.0",
  "org.typelevel"               %%  "cats-kernel"     % "2.0.0",
  "org.typelevel"               %% "cats-effect"      % "2.0.0",
  "ch.qos.logback"              %   "logback-classic"   % "1.2.3",
  "com.typesafe.scala-logging"  %%  "scala-logging"    % "3.9.2",
  "org.scalatestplus.play"      %%  "scalatestplus-play"   % "4.0.1"     % Test,
  "com.h2database"              %   "h2"              % "1.4.197",
  "com.softwaremill.macwire"    %%  "macros"            % "2.3.7"  % "provided",
  "com.softwaremill.macwire"    %% "macrosakka" % "2.3.6" % "provided",
  "com.softwaremill.macwire"    %% "util"             % "2.3.6",
  "com.softwaremill.macwire"    %% "proxy"            % "2.3.6",
  "io.circe"                    %%  "circe-parser"    % circeVersion,
  "com.chuusai"                 %%  "shapeless"       % "2.4.0-M1"
)

// for @Lenses macro support
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

scalacOptions ++= Seq(
//  "-feature",
//  "-deprecation",
//  "-Xfatal-warnings",
//  "-language:reflectiveCalls",
  "-Ypartial-unification"
//  "-Xlog-implicits",
//  "-Xprint:jvm"
)
