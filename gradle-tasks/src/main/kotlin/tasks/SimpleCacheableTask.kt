package tasks

import action.PrepSourceAction
import data.PrepConfig
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.api.tasks.options.Option
import org.gradle.process.ExecOperations
import org.gradle.work.FileChange
import org.gradle.work.Incremental
import org.gradle.work.InputChanges
import java.io.File
import javax.inject.Inject

@CacheableTask
abstract class SimpleCacheableTask : DefaultTask() {

    @get:Inject
    abstract val execOperations: ExecOperations

    @get:Inject
    protected abstract val objects: ObjectFactory

    @get:Option(option = "excludeTasks", description = "list of tasks to exclude")
    @get:Input
    abstract val excludeTasks: ListProperty<String>

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

    init {
        prepConfig.sth.convention("sth")
    }

    @TaskAction
    fun generate(inputs: InputChanges) {

        execOperations.javaexec {
            mainClass.set("")
            classpath = objects.fileCollection().from(prepConfig.configuration.get())
        }

        logger.info("excludeTasks: ${excludeTasks.get()}")

        logger.info(prepConfig.toString())

        incrementalFiles.map {
            logger.info("assigned natsource: ${it.path}")
        }

        val prepSourceAction = objects.newInstance(PrepSourceAction::class.java)
        prepSourceAction.action(this, prepConfig.prepConfigResources.get()[0].path, prepConfig.prepOut)

        prepConfig.prepOut.file("output.txt").get().asFile.writeText("")
        if (inputs.isIncremental) {
            println("CHANGED: " + inputs.getFileChanges(incrementalFiles))
            inputs.getFileChanges(incrementalFiles).forEach { it: FileChange ->
                prepConfig.prepOut.file("output.txt").get().asFile.appendText("\nout - " + it.file.name)
            }
        } else {
            println("FULL")
            incrementalFiles.asFileTree.files.forEach { it: File ->
                prepConfig.prepOut.file("output.txt").get().asFile.appendText("\nout - " + it.name)
            }
        }
    }
}