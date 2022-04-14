name := """scala-play-react-seed"""

version := "1.0-SNAPSHOT"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(
    scalaVersion := "2.13.7",
    watchSources ++= (baseDirectory.value / "public/ui" ** "*").get,
    libraryDependencies ++= Seq(
      "com.softwaremill.macwire" %% "macros" % "2.3.7" % "provided",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "org.postgresql" % "postgresql" % "42.2.18",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "it,test"
    ),
    Defaults.itSettings,
    IntegrationTest / scalaSource := baseDirectory.value / "/it",
    dependencyClasspath in IntegrationTest := (dependencyClasspath in IntegrationTest).value ++ (exportedProducts in Test).value,
    (managedClasspath in IntegrationTest) += (packageBin in Assets).value
  )
