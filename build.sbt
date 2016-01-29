import _root_.sbtassembly.AssemblyPlugin.autoImport._
import _root_.sbtassembly.PathList

name := "jobserver-test"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "Job Server Bintray" at "https://dl.bintray.com/spark-jobserver/maven"

libraryDependencies += "spark.jobserver" %% "job-server-api" % "0.6.1" % "provided"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.2" % "provided"

assemblyJarName in assembly := s"${name.value}-fat.jar"

// Add exclusions, provided...
assemblyMergeStrategy in assembly := {
  {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }
}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)



