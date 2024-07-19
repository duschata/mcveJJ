package tasks

import data.PrepConfigResource
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.work.Incremental
import org.gradle.work.InputChanges
import javax.inject.Inject

@CacheableTask
abstract class SimpleCacheableTask : DefaultTask() {

    @get:Inject
    protected abstract val objects: ObjectFactory

    @get:InputFiles
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val incrementalFiles: ConfigurableFileCollection

    @get:OutputDirectory
    abstract val outDir: DirectoryProperty

    @get:Nested
    abstract val natProjectResources: ListProperty<PrepConfigResource>


    fun natProjectResource(action: Action<PrepConfigResource>) {
        val natProjectResource = objects.newInstance(PrepConfigResource::class.java)
        action.execute(natProjectResource)
        natProjectResources.add(natProjectResource)
    }

    init {
        outDir.convention(project.layout.buildDirectory.dir("tmp"))
    }

    @TaskAction
    fun generate(inputs: InputChanges) {
        incrementalFiles.map {
            logger.info("assigned natsource: ${it.path}")
        }

        if (inputs.isIncremental) {
            println("CHANGED: " + inputs.getFileChanges(incrementalFiles))
            inputs.getFileChanges(incrementalFiles).forEach {
                outDir.file("output.txt").get().asFile.appendText("\nout - " + it.file.name)
            }
        } else {
            println("FULL")
            incrementalFiles.asFileTree.files.forEach {
                outDir.file("output.txt").get().asFile.appendText("\nout - " + it.name)
            }
        }
    }
}