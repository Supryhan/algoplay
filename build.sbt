name := "algoplay"

version := "0.1"

scalaVersion := "2.13.3"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

//addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.1.2")

//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")
//resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
//addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.10")

val monocle = "2.1.0"
val circeVersion = "0.13.0"
val akkaVersion = "2.6.8"
val akkaHttpVersion = "10.2.0"

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
  "org.scalacheck"              %% "scalacheck"       % "1.14.3"   % Test,
  "org.scalatest"               %% "scalatest"        % "3.3.0-SNAP2"    % Test,
  "org.scalatest"               %% "scalatest-funsuite" % "3.3.0-SNAP2"  % Test,
  "org.scala-lang"              %  "scala-reflect"    % scalaVersion.value,
  "org.scala-lang"              %  "scala-compiler"   % scalaVersion.value  % Provided,
  "org.typelevel"               %% "cats-core"        % "2.2.0",
  "org.typelevel"               %% "cats-kernel"      % "2.0.0",
  "org.typelevel"               %% "cats-effect"      % "2.2.0-RC3",
  "org.scalaz"                  %  "scalaz-zio_2.13.0-M5" % "1.0-RC1",
  "ch.qos.logback"              %  "logback-classic"  % "1.2.3",
  "com.typesafe.scala-logging"  %% "scala-logging"    % "3.9.2",
  "org.scalatestplus.play"      %% "scalatestplus-play"   % "5.1.0"    % Test,
  "com.h2database"              %  "h2"               % "1.4.200",
  "com.softwaremill.macwire"    %% "macros"           % "2.3.7"         % Provided,
  "com.softwaremill.macwire"    %% "macrosakka"       % "2.3.7" % Provided,
  "com.softwaremill.macwire"    %% "util"             % "2.3.7",
  "com.softwaremill.macwire"    %% "proxy"            % "2.3.7",
  "io.circe"                    %% "circe-parser"     % circeVersion,
  "com.chuusai"                 %% "shapeless"        % "2.4.0-M1"
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
