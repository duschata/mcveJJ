package tasks

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class CopyBulkTaskOld : DefaultTask() {

    @get:Inject
    abstract val fileSystemOperations: FileSystemOperations

    @get:Input
    abstract val fromFile: Property<String>

    @get:Input
    abstract val intoFile: Property<String>

    @get:OutputDirectories
    abstract val directories: ListProperty<Directory>

    fun directories(action: Action<ListProperty<Directory>>) {
        logger.info("never passed :-(")
        action.execute(directories)
        directories.get().forEach { directory ->
            logger.info("adding: ${directory.file(fromFile.get())}")
            inputFiles.from(directory.file(fromFile.get()))
            outputFiles.from(directory.file(intoFile.get()))
        }
    }

    @get:InputFiles
    @get:Optional
    abstract val inputFiles: ConfigurableFileCollection

    @get:OutputFiles
    @get:Optional
    abstract val outputFiles: ConfigurableFileCollection


    @TaskAction
    fun apply() {
        directories.get().forEach { directory ->
            fileSystemOperations.apply {
                copy {
                    from(directory)
                    into(directory)
                    include(fromFile.get())
                    rename(fromFile.get(), intoFile.get())
                }
            }
        }
    }
}