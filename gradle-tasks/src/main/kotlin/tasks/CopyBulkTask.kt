package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemOperations
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

    @get:Input
    abstract val fromFile: Property<String>

    @get:Input
    abstract val intoFile: Property<String>

    @get:Internal
    abstract val directories: ListProperty<Directory>

    @get:InputFiles
    val inputFiles: Provider<List<Directory>> = directories.map { list ->
        list.map { dir -> dir.dir(fromFile).get() }
    }

    @get:OutputFiles
    val outputFiles: Provider<List<Directory>> = directories.map { list ->
        list.map { dir -> dir.dir(intoFile).get() }
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