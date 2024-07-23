package tasks

import data.PrepConfig
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
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

    @get:Nested
    abstract val prepConfig: PrepConfig


    fun prepConfig(action: Action<PrepConfig>) {
        action.execute(prepConfig)
        incrementalFiles.from(prepConfig.prepConfigResources.get().map { it.path })
    }

    @TaskAction
    fun generate(inputs: InputChanges) {
        incrementalFiles.map {
            logger.info("assigned natsource: ${it.path}")
        }

       prepConfig.prepOut.file("output.txt").get().asFile.writeText("")
        if (inputs.isIncremental) {
            println("CHANGED: " + inputs.getFileChanges(incrementalFiles))
            inputs.getFileChanges(incrementalFiles).forEach {
                prepConfig.prepOut.file("output.txt").get().asFile.appendText("\nout - " + it.file.name)
            }
        } else {
            println("FULL")
            incrementalFiles.asFileTree.files.forEach {
                prepConfig.prepOut.file("output.txt").get().asFile.appendText("\nout - " + it.name)
            }
        }
    }
}