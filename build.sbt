
lazy val root =
  project
    .in(file("."))
    .settings(
      organization := "com.hunorkovacs",
      name := "zio-http4s-zlayer-example",
      version := "0.0.1-SNAPSHOT",
      scalaVersion := "2.13.3",
      libraryDependencies ++= Seq(
        "org.http4s" %% "http4s-blaze-server" % versions.http4s,
        "org.http4s" %% "http4s-dsl"          % versions.http4s,
        "dev.zio"    %% "zio"                 % "1.0.2",
        "dev.zio"    %% "zio-interop-cats"    % "2.2.0.1"
      )
    )

lazy val versions = new {
  val http4s = "1.0.0-M4"
}
