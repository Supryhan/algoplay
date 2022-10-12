name := "algoplay"

version := "0.1"

scalaVersion := "2.13.6"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

//addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.1.2")

//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")
//resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
//addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.10")

val monocle = "2.1.0"
val circeVersion = "0.14.1"
val macwireVersion = "2.5.2"
val akkaVersion = "2.6.18"
val akkaHttpVersion = "10.2.6"
val zioVersion = "2.0.0-RC1"
val http4sVersion = "0.23.16"

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka"           %% "akka-http"        % akkaHttpVersion,
  "com.typesafe.akka"           %% "akka-stream"      % akkaVersion,
  "com.typesafe.akka"           %% "akka-slf4j"       % akkaVersion,
  "com.typesafe.akka"           %% "akka-testkit"     % akkaVersion  % Test,
  "com.github.julien-truffaut"  %% "monocle-core"     % monocle,
  "com.github.julien-truffaut"  %% "monocle-generic"  % monocle,
  "com.github.julien-truffaut"  %% "monocle-macro"    % monocle,
  "com.github.julien-truffaut"  %% "monocle-state"    % monocle,
  "com.github.julien-truffaut"  %% "monocle-refined"  % monocle,
  "com.github.julien-truffaut"  %% "monocle-law"      % monocle    % Test,
  "eu.timepit"                  %% "refined"          % "0.9.28",
  "eu.timepit"                  %% "refined-cats"     % "0.9.28",
  "io.estatico"                 %% "newtype"          % "0.4.4",
  "org.scalacheck"              %% "scalacheck"       % "1.15.4"   % Test,
  "org.scalactic"               %% "scalactic"        % "3.2.9",
  "org.scalatest"               %% "scalatest"        % "3.2.9"    % Test,
  "org.scalatest"               %% "scalatest-funsuite" % "3.2.9"  % Test,
  "org.specs2"                  %% "specs2-core"      % "4.16.1"  % Test,
  "org.specs2"                  %% "specs2-junit"     % "4.16.1"  % Test,
  "org.scala-lang"              %  "scala-reflect"    % scalaVersion.value,
  "org.scala-lang"              %  "scala-compiler"   % scalaVersion.value  % Provided,
  "org.typelevel"               %% "cats-core"        % "2.7.0",
  "org.typelevel"               %% "cats-kernel"      % "2.7.0",
  "org.typelevel"               %% "cats-effect"      % "3.3.0",
  "dev.zio"                     %% "zio"              % zioVersion,
  "ch.qos.logback"              %  "logback-classic"  % "1.2.10",
  "com.typesafe.scala-logging"  %% "scala-logging"    % "3.9.4",
  "org.scalatestplus.play"      %% "scalatestplus-play"   % "5.1.0"    % Test,
  "com.h2database"              %  "h2"               % "2.0.204",
  "com.softwaremill.macwire"    %% "macros"           % macwireVersion % Provided,
  "com.softwaremill.macwire"    %% "macrosakka"       % macwireVersion % Provided,
  "com.softwaremill.macwire"    %% "util"             % macwireVersion,
  "com.softwaremill.macwire"    %% "proxy"            % macwireVersion,
  "io.circe"                    %% "circe-parser"     % circeVersion,
  "com.chuusai"                 %% "shapeless"        % "2.3.7",
  "com.github.pureconfig"       %% "pureconfig"       % "0.17.1",
  "org.http4s"                  %% "http4s-dsl"       % http4sVersion,
  "org.http4s"                  %% "http4s-ember-server" % http4sVersion,
  "org.http4s"                  %% "http4s-ember-client" % http4sVersion,
  "org.typelevel"               %% "squants"          % "1.6.0"
)

// for @Lenses macro support
//addCompilerPlugin("org.scalamacros" % "paradise_2.13.0-M3" % "2.1.1" cross CrossVersion.full)

scalacOptions ++= Seq(
//  "-feature",
//  "-deprecation",
//  "-Xfatal-warnings",
//  "-language:reflectiveCalls",
//  "-Ypartial-unification",
    "-Ymacro-annotations"
//  "-Xlog-implicits",
//  "-Xprint:jvm"
)
scalacOptions in Test ++= Seq("-Yrangepos")
