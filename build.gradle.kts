import data.PrepConfig
import data.PrepConfigResource
import tasks.SimpleCacheableTask

plugins {
    id("gradle-tasks")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}


val firstPrepConfig = PrepConfig(
    "nat-code",
    project.layout.projectDirectory.dir("nat-sources/first"),
    "individual configuration for this execute"
)
val secondPrepConfig = PrepConfig(
    "nat-code-helper",
    project.layout.projectDirectory.dir("nat-sources/second"),
    "individual configuration for this execute"
)

val prepConfigs = listOf(firstPrepConfig, secondPrepConfig)

tasks.register<SimpleCacheableTask>("simpleCacheableTask") {
    // das geht, ist aber sinnlos, wenn die nächste Zeile nicht geht
    sourceFiles.from(prepConfigs.map { it.path })
    // das schlägt fehl
    // natProjects = prepConfigs

    // das geht, ist aber keine Liste
    natProjectResource {
        naturalRepo = "nat-code"
        path = project.layout.projectDirectory.dir("nat-sources/first")
        property = "individual configuration for this execute"
    }

    //wie kann ich hier elemente hinzufügen?
    natProjectResources = listOf()


}