package org.sbuild.plugins.scalac

import org.sbuild._
import java.io.File

case class Scalac(
    compileTargetName: String,
    cleanTargetName: Option[String] = None,
    classpath: TargetRefs = TargetRefs(),
    targetDir: File,
    scalaVersion: Option[String] = None,
    compilerClasspath: Option[TargetRefs] = None,
    sources: Option[TargetRefs] = None,
    srcDirs: Seq[File] = Seq(),
    encoding: String = "UTF-8",
    deprecation: Option[Boolean] = None,
    verbose: Option[Boolean] = None,
    //    source: Option[String] = None,
    target: Option[String] = None,
    debugInfo: Option[String] = None,
    fork: Boolean = true,
    additionalScalacArgs: Seq[String] = Seq(),
    dependsOn: TargetRefs = TargetRefs()) {

  def compileTargetName(compileTargetName: String): Scalac = copy(compileTargetName = compileTargetName)

  def cleanTargetName(cleanTargetName: Option[String]): Scalac = copy(cleanTargetName = cleanTargetName)
  def cleanTargetName(cleanTargetName: String): Scalac = copy(cleanTargetName = Some(cleanTargetName))

  def classpath(classpath: TargetRefs): Scalac = copy(classpath = classpath)
  def addClasspath(classpath: TargetRefs): Scalac = copy(classpath = this.classpath ~ classpath)

  def targetDir(targetDir: File): Scalac = copy(targetDir = targetDir)

  def scalaVersion(scalaVersion: Option[String]): Scalac = copy(scalaVersion = scalaVersion)
  def scalaVersion(scalaVersion: String): Scalac = copy(scalaVersion = Some(scalaVersion))

  def compilerClasspath(compilerClasspath: Option[TargetRefs]): Scalac = copy(compilerClasspath = compilerClasspath)
  def compilerClasspath(compilerClasspath: TargetRefs): Scalac = copy(compilerClasspath = Some(compilerClasspath))

  def sources(sources: Option[TargetRefs]): Scalac = copy(sources = sources)
  def sources(sources: TargetRefs): Scalac = copy(sources = Some(sources))

  def srcDirs(srcDirs: File*): Scalac = copy(srcDirs = srcDirs)
  def addSrcDirs(srcDirs: File*): Scalac = copy(srcDirs = this.srcDirs ++ srcDirs)

  def encoding(encoding: String): Scalac = copy(encoding = encoding)

  def deprecation(deprecation: Option[Boolean]): Scalac = copy(deprecation = deprecation)
  def deprecation(deprecation: Boolean): Scalac = copy(deprecation = Some(deprecation))

  def verbose(verbose: Option[Boolean]): Scalac = copy(verbose = verbose)
  def verbose(verbose: Boolean): Scalac = copy(verbose = Some(verbose))

  //  def source(source: Option[String]): Scalac = copy(source = source)
  //  def source(source: String): Scalac = copy(source = Some(source))

  def target(target: Option[String]): Scalac = copy(target = target)
  def target(target: String): Scalac = copy(target = Some(target))

  def debugInfo(debugInfo: Option[String]): Scalac = copy(debugInfo = debugInfo)
  def debugInfo(debugInfo: String): Scalac = copy(debugInfo = Some(debugInfo))

  def fork(fork: Boolean): Scalac = copy(fork = fork)

  def additionalScalacArgs(additionalScalacArgs: String*): Scalac = copy(additionalScalacArgs = additionalScalacArgs)
  def addAdditionalScalacArgs(additionalScalacArgs: String*): Scalac = copy(additionalScalacArgs = this.additionalScalacArgs ++ additionalScalacArgs)

  def dependsOn(dependsOn: TargetRefs): Scalac = copy(dependsOn = dependsOn)

  override def toString = getClass().getSimpleName() +
    "(compileTargetName=" + compileTargetName +
    ",cleanTargetName=" + cleanTargetName +
    ",classpath=" + classpath +
    ",targetDir=" + targetDir +
    ",compilerClasspath=" + compilerClasspath +
    ",sources=" + sources +
    ",srcDirs=" + srcDirs +
    ",encoding=" + encoding +
    ",deprecation=" + deprecation +
    ",verbose=" + verbose +
    //    ",source=" + source +
    ",target=" + target +
    ",debugInfo=" + debugInfo +
    ",fork=" + fork +
    ",additionalScalacArgs=" + additionalScalacArgs +
    ",dependsOn=" + dependsOn +
    ")"
}
