lazy val Benchmark = config("bench") extend Test

lazy val ScalaMeter = new TestFramework("org.scalameter.ScalaMeterFramework")

lazy val buildSettings = Seq(
  organization := "com.github",
  name := "flink-shapeless",
  scalaVersion := "2.12.7"
)

lazy val benchSettings = Seq(
  testFrameworks += ScalaMeter,
  parallelExecution in Benchmark := false,
  logBuffered := false,
  testOptions in Benchmark += Tests.Argument(ScalaMeter, "-CresultDir", "docs/benchmarks")
)

lazy val coverageSettings = Seq[Def.SettingsDefinition](
  coverageMinimum := 70,
  coverageFailOnMinimum := false,
  coverageExcludedFiles := ".*/src/test/.*;.*/src/bench/.*"
)

val versions = new {
  val flink = "1.11.2"
  val shapeless = "2.3.3"
  val scalatest = "3.2.3"
  val scalacheck = "1.13.5"
  val checkless = "1.1.8"
  val scala_meter = "0.8.2"
  val scala_arm = "2.0"
  val utils = "1.07.00"
  val slf4j = "1.7.25"
  //val paradise = "2.1.0"
}

lazy val compileDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % versions.flink,
  "com.chuusai" %% "shapeless" % versions.shapeless
)

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % versions.scalatest,
  "org.scalacheck" %% "scalacheck" % versions.scalacheck,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % versions.checkless,
  "com.jsuereth" %% "scala-arm" % versions.scala_arm,
  "org.ostermiller" % "utils" % versions.utils,
  "org.slf4j" % "slf4j-nop" % versions.slf4j
).map(_ % "test")

lazy val benchDependencies = Seq(
  "com.storm-enroute" %% "scalameter" % versions.scala_meter
).map(_ % "bench")

lazy val commonSettings = Seq(
  scalacOptions := Seq(
    "-encoding", "UTF-8",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-language:higherKinds",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-Xfatal-warnings",
    "-Xfuture",
    "-Xlint"
  ),
  libraryDependencies ++= compileDependencies ++ testDependencies ++ benchDependencies
)

lazy val root = Project("flink-shapeless", file("."))
  .settings(Defaults.coreDefaultSettings: _*)
  .settings(buildSettings: _*)
  .settings(commonSettings: _*)
  .settings(benchSettings: _*)
  .settings(coverageSettings: _*)

  .configs(Benchmark)
  .settings(inConfig(Benchmark)(Defaults.testSettings): _*)
