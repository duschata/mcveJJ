package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class CopyBulkTask : DefaultTask() {

    @get:Inject
    abstract val fileSystemOperations: FileSystemOperations

    @get:Internal
    abstract val fromFile: Property<String>

    @get:Internal
    abstract val intoFile: Property<String>

    @get:Internal
    abstract val directories: ListProperty<Directory>

    @get:InputFiles
    abstract val inputFiles: ListProperty<RegularFile>

    @get:OutputFiles
    abstract val outputFiles:  ListProperty<RegularFile>

    init {
        inputFiles.convention(directories.map { list ->
            list.map { dir -> dir.file(fromFile).get() }
        })
        outputFiles.convention(directories.map { list ->
            list.map { dir -> dir.file(intoFile).get() }
        })
    }

    @TaskAction
    fun apply() {
        directories.get().forEach { directory ->
            fileSystemOperations.copy {
                from(directory)
                into(directory)
                include(fromFile.get())
                rename(fromFile.get(), intoFile.get())
            }
        }
    }
}