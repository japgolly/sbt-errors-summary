package sbt
package errorssummary

import sbt.AutoPlugin
import sbt.Keys.{compile, compilerReporter, sourceDirectory, streams}

object Plugin extends AutoPlugin {
  override def requires = sbt.plugins.JvmPlugin
  override def trigger  = allRequirements

  override def projectSettings: Seq[Setting[_]] =
    Seq(
      compilerReporter in (Compile, compile) := {
        val parent    = (compilerReporter in (Compile, compile)).value
        val logger    = streams.value.log
        val sourceDir = sourceDirectory.value.getAbsolutePath
        Some(new ConciseReporter(logger, sourceDir, parent))
      }
    )
}