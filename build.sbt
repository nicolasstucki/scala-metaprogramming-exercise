val dottyVersion = "0.25.0-RC2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-simple",
    version := "0.1.0",

    scalaVersion := dottyVersion,

    libraryDependencies ++= Seq(
      "ch.epfl.lamp" % "dotty_0.25" % dottyVersion,
      "ch.epfl.lamp" % "dotty_0.25" % dottyVersion % "test->runtime",
      "ch.epfl.lamp" %% "dotty-staging" % dottyVersion,
      "com.novocode" % "junit-interface" % "0.11" % "test"
    )
  )
