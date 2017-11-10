name := """example"""
organization := "com.example"

retrieveManaged := false
scalacOptions in ThisBuild ++= Seq("-feature")

version := "1.0"

lazy val scalaV = "2.11.8"

scalaVersion := scalaV

import com.typesafe.sbt.SbtScalariform._

import scalariform.formatter.preferences._

lazy val shared = crossProject.crossType(CrossType.Pure).
  settings(
    scalaVersion := scalaV,
    version := "1.0"
  ).
  jsConfigure(_ enablePlugins ScalaJSWeb )

lazy val sharedJVM = shared.jvm
lazy val sharedJS = shared.js

lazy val jvm = (project in file("jvm")).
  settings(
    scalaVersion := scalaV,
    version := "1.0",
    scalaJSProjects := Seq(js),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    resolvers ++= Seq(Resolver.jcenterRepo, Resolver.sonatypeRepo("snapshots")),
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.8",
      "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
      filters,
      "com.vmunier" %% "scalajs-scripts" % "1.0.0",
      "org.webjars" %% "webjars-play" % "2.5.0-4",
      "org.webjars.npm" % "react" % "15.4.1",
      "org.webjars" % "react" % "15.3.2",
      "org.webjars" % "bootstrap" % "3.1.1-2",
      "net.codingwell" %% "scala-guice" % "4.0.1",
      ws,
      "org.scala-lang.modules" %% "scala-pickling" % "0.10.1",
      specs2 % Test,
      cache
    ),
    // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
    EclipseKeys.preTasks := Seq(compile in Compile),

    routesGenerator := InjectedRoutesGenerator,
    routesImport += "utils.route.Binders._",
    scalacOptions ++= Seq(
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly.
      "-unchecked", // Enable additional warnings where generated code depends on assumptions.
      "-Xfatal-warnings", // Fail the compilation if there are any warnings.
      "-Xlint", // Enable recommended additional warnings.
      "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
      "-Ywarn-dead-code", // Warn when dead code is identified.
      "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
      "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
      "-Ywarn-numeric-widen" // Warn when numerics are widened.
    ),
    defaultScalariformSettings,
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(FormatXml, false)
      .setPreference(DoubleIndentClassDeclaration, false)
      .setPreference(DanglingCloseParenthesis, Preserve),
    libraryDependencies += "com.github.ghik" %% "silencer-lib" % "0.5",
    addCompilerPlugin("com.github.ghik" %% "silencer-plugin" % "0.5")
  ).
  enablePlugins(PlayScala).
  dependsOn(sharedJVM)

lazy val js = (project in file("js")).
  settings(
    scalaVersion := scalaV,
    version := "1.0",
    scalaJSUseMainModuleInitializer := true,
    scalaJSUseMainModuleInitializer in Test := false,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1"
    )
  ).
  enablePlugins(ScalaJSPlugin, ScalaJSWeb).
  dependsOn(sharedJS)

// loads the server project at sbt startup
onLoad in Global := (Command.process("project jvm", _: State)) compose (onLoad in Global).value

EclipseKeys.withSource := true

EclipseKeys.createSrc := EclipseCreateSrc.Default



