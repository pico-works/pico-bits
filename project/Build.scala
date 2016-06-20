import sbt.Keys._
import sbt._

object Build extends sbt.Build {  
  val pico_unsigned             = "org.pico"        %%  "unsigned"                  % "3.7.2"
  val specs2_core               = "org.specs2"      %%  "specs2-core"               % "3.7.2"
  val specs2_scalacheck         = "org.specs2"      %%  "specs2-scalacheck"         % "3.7.2"

  implicit class ProjectOps(self: Project) {
    def standard(theDescription: String) = {
      self
          .settings(scalacOptions in Test ++= Seq("-Yrangepos"))
          .settings(publishTo := Some("Releases" at "s3://dl.john-ky.io/maven/releases"))
          .settings(description := theDescription)
          .settings(isSnapshot := true)
          .settings(scalacOptions ++= scalacFlags(scalaVersion.value))
          .settings(scalacOptions in (Compile, doc) += "-no-link-warnings")
    }

    def notPublished = self.settings(publish := {}).settings(publishArtifact := false)

    def libs(modules: ModuleID*) = self.settings(libraryDependencies ++= modules)

    def testLibs(modules: ModuleID*) = self.libs(modules.map(_ % "test"): _*)

    def scalacFlags(scalaVersion: String) = {
      val commonFlags = Seq(
        "-deprecation",
        "-encoding", "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-unchecked",
        "-Xfatal-warnings",
        "-Xlint",
        "-Yno-adapted-args",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Xfuture")

      val versionSpecificFlags = CrossVersion.partialVersion(scalaVersion) match {
        case Some((2, scalaMajor)) if scalaMajor >= 11 => Seq("-Ywarn-unused-import")
        case _ => Seq.empty
      }

      commonFlags ++ versionSpecificFlags
    }
  }

  lazy val `pico-fake` = Project(id = "pico-fake", base = file("pico-fake"))
      .standard("Fake project").notPublished
      .testLibs(specs2_core)

  lazy val `pico-bits` = Project(id = "pico-bits", base = file("pico-bits"))
      .standard("Tiny bits value syntax support library")
      .testLibs(specs2_core, specs2_scalacheck)

  lazy val all = Project(id = "pico-bits-project", base = file("."))
      .notPublished
      .aggregate(`pico-bits`, `pico-fake`)
}
