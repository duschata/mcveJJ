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
    prepConfig {
//        sth = "global conf"
        prepOut = layout.buildDirectory.dir("tmp")
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