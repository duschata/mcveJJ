import tasks.PrepConfig
import tasks.SimpleCacheableTask

plugins {
    id("gradle-tasks")
}


val firstPrepConfig = PrepConfig("nat-code", project.layout.projectDirectory.dir("nat-sources/first"), "")
val secondPrepConfig = PrepConfig("nat-code", project.layout.projectDirectory.dir("nat-sources/second"), "")

val prepConfigs = listOf(firstPrepConfig, secondPrepConfig)

tasks.register<SimpleCacheableTask>("simpleCacheableTask") {
    sourceFiles.from(prepConfigs.map { it.path })
    natProjects = prepConfigs
}