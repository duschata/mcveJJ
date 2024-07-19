import tasks.SimpleCacheableTask

plugins {
    id("gradle-tasks")
}

tasks.register<SimpleCacheableTask>("simpleCacheableTask") {
    natProjectResource {
        naturalRepo = "nat-code"
        path = project.layout.projectDirectory.dir("nat-sources/first")
        property = "individual configuration for this execute"
    }
    natProjectResource {
        naturalRepo = "nat-code-helper"
        path = project.layout.projectDirectory.dir("nat-sources/second")
        property = "individual configuration for this execute"
    }
//    use map instead of get, but actually this construct is not needed here...
//    incrementalFiles.from(natProjectResources.map { it.map { prepConfigResource -> prepConfigResource.path } }, layout.projectDirectory.dir("other-source"))
    incrementalFiles.from(layout.projectDirectory.dir("other-source"))
}