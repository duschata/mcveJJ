import tasks.SimpleCacheableTask

plugins {
    id("gradle-tasks")
}

global {
    natSourcesPath = layout.projectDirectory.dir("nat-sources")
    natSourcesFirst = "first"
    natSourcesSecond = "second"
}

tasks.register<SimpleCacheableTask>("simpleCacheableTask") {
    natProjectResource {
        naturalRepo = "nat-code"
        path = global.natSourcesPath.flatMap { path -> path.dir(global.natSourcesFirst.map { it }) }
        property = "individual configuration for this execute"
    }
    natProjectResource {
        naturalRepo = "nat-code-helper"
        path = global.natSourcesPath.flatMap { path -> path.dir(global.natSourcesSecond.map { it }) }
        property = "individual configuration for this execute"
    }
//    use map instead of get, but actually this construct is not needed here...
//    incrementalFiles.from(natProjectResources.map { it.map { prepConfigResource -> prepConfigResource.path } }, layout.projectDirectory.dir("other-source"))
    incrementalFiles.from(layout.projectDirectory.dir("other-source"))
}