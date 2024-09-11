import tasks.*

plugins {
    id("gradle-tasks")
}

repositories {
    mavenCentral()
}

global {
    natSourcesPath = layout.projectDirectory.dir("nat-sources")
    natSourcesFirst = "first"
    natSourcesSecond = "second"
}

val cocoToolsRuntimeOnly: Configuration by configurations.creating

dependencies {
    cocoToolsRuntimeOnly("org.apache.commons:commons-lang3:3.17.0")
}

tasks.register<ExecuteBulkTask>("taskWithList") {
    executable = File("/home/tom/bin/meeclipse/meeclipse23Plain/eclipse")

    projectPaths = listOf(
        objects.directoryProperty().value(layout.projectDirectory.dir("nat-sources")),
        objects.directoryProperty().value(layout.projectDirectory.dir("other-source"))
    )

    eclipseProject {
        projectDirectory = layout.projectDirectory.dir("nat-sources")
    }
    eclipseProject {
        projectDirectory = layout.projectDirectory.dir("other-source")
    }
}

tasks.register<SimpleCacheableTask>("simpleCacheableTask") {
    prepConfig {
//        sth = "global conf"
        prepOut = layout.buildDirectory.dir("tmp")
        configuration.set(cocoToolsRuntimeOnly.resolve())
        prepConfigResource {
            repoName = "first repo"
            path = global.natSourcesPath.flatMap { path -> path.dir(global.natSourcesFirst) }
            additionalProperty = "additional"
        }
        prepConfigResource {
            repoName = "second repo"
            path = global.natSourcesPath.flatMap { path -> path.dir(global.natSourcesSecond) }
            additionalProperty = "additional"
        }
    }

//    use map instead of get, but actually this construct is not needed here...
//    incrementalFiles.from(natProjectResources.map { it.map { prepConfigResource -> prepConfigResource.path } }, layout.projectDirectory.dir("other-source"))
    incrementalFiles.from(layout.projectDirectory.dir("other-source"))
}

val taskWithOutput = tasks.register<TaskWithOutput>("taskWithOutput") {
    input.set("trollollo")
    output.set(layout.buildDirectory.dir("bla"))
}
tasks.register<TaskWithInput>("taskWithInput") {
    input.set(taskWithOutput.get().output)
}

tasks.register<TaskWithInputList>("taskWithInputList") {
    inputList {
        input.set(taskWithOutput.get().output)
    }
}