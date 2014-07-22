package org.sbuild.plugins.scalac

import org.sbuild._

class ScalacPlugin(implicit project: Project) extends Plugin[Scalac] {

  def create(name: String): Scalac = {
    val compileTargetName = s"scalac-${name}"
    val cleanTargetName = s"clean-scalac-${name}"
    val classpath = TargetRefs()
    val targetDir = Path(s"target/scalac-${name}-classes")
    val srcDirs = Seq(Path(s"src/${name}/scala"))

    Scalac(
      compileTargetName = compileTargetName,
      cleanTargetName = Some(cleanTargetName),
      classpath = classpath,
      targetDir = targetDir,
      srcDirs = srcDirs
    )
  }

  def applyToProject(instances: Seq[(String, Scalac)]): Unit = instances.foreach {
    case (name, scalac) =>

      val sources: TargetRefs = scalac.sources match {
        case Some(s) => s
        case None => scalac.srcDirs.map(dir => TargetRef(s"scan:${dir};regex=.*\\.(java|scala)"))
      }

      val compilerClasspath: TargetRefs = scalac.compilerClasspath.getOrElse {
        scalac.scalaVersion match {
          case Some(v) => ScalacTask.compilerClasspath(v)
          case _ => TargetRefs()
        }
      }
      val dependencies: TargetRefs = scalac.dependsOn ~ compilerClasspath ~ scalac.classpath ~~ sources

      scalac.cleanTargetName.map { cleanTargetName =>
        Target(s"phony:${cleanTargetName}").evictCache(scalac.compileTargetName) exec {
          scalac.targetDir.deleteRecursive
        }
      }

      Target(s"phony:${scalac.compileTargetName}").cacheable dependsOn dependencies exec { ctx: TargetContext =>

        if (sources.files.isEmpty) {
          // TODO: Improve, if for a dedicated error API in SBuild
          // project.monitor.warn("No sources files found.")
          // ctx.error("No source files found.")
          throw new RuntimeException("No source files found.")
        }

        val compiler = new ScalacTask(
          compilerClasspath = compilerClasspath.files,
          classpath = scalac.classpath.files,
          sources = sources.files,
          destDir = scalac.targetDir,
          encoding = scalac.encoding,
          fork = scalac.fork,
          additionalScalacArgs = scalac.additionalScalacArgs
        )

        scalac.deprecation.map { d => compiler.deprecation = d }
        scalac.verbose.map { d => compiler.verbose = d }
        //        scalac.source.map { d => compiler.source = d }
        scalac.target.map { d => compiler.target = d }
        scalac.debugInfo.map { d => compiler.debugInfo = d }

        compiler.execute

        scalac.targetDir.listFilesRecursive.foreach { f => ctx.attachFile(f) }
      }
  }

}