package tasks

import data.PrepConfig
import data.PrepConfigResource
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

@CacheableTask
abstract class SimpleCacheableTask : DefaultTask() {

    @get:InputFiles
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val sourceFiles: ConfigurableFileCollection

    @get:OutputDirectory
    abstract val outDir: DirectoryProperty

//    @get:Input
//    abstract val natProjects: ListProperty<PrepConfig>

    @get:Nested
    abstract val natProjectResource : PrepConfigResource

    fun natProjectResource (action: Action<PrepConfigResource>) {
        action.execute(natProjectResource)
    }

    @get:Nested
    abstract val natProjectResources: ListProperty<PrepConfigResource>

    init {
        outDir.convention(project.layout.buildDirectory.dir("tmp"))
    }

    @TaskAction
    fun generate(inputs: InputChanges) {
        natProjectResources.get().forEach{
            logger.info("############################")
            logger.info("${it.naturalRepo.get() }, ${it.path.get().asFile.path}, ${it.property.get()}")
        }


        if (inputs.isIncremental) {
            println("CHANGED: " + inputs.getFileChanges(sourceFiles))
            inputs.getFileChanges(sourceFiles).forEach {
                outDir.file("output.txt").get().asFile.appendText("\nout - " + it.file.name)
            }
        } else {
            println("FULL")
            sourceFiles.asFileTree.files.forEach {
                outDir.file("output.txt").get().asFile.appendText("\nout - " + it.name)
            }
        }
    }
}