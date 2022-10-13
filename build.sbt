ThisBuild / scalaVersion     := "3.2.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "zio-http-middleware-add-up",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"      % "2.0.2",
      "dev.zio" %% "zio-test" % "2.0.2" % Test,
      "io.d11"  %% "zhttp"    % "2.0.0-RC10",
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
  )
