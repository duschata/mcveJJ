package action

import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import java.io.File

abstract class ExecuteBulkAction {

    fun apply(task: Task, executable: Property<File>, project: DirectoryProperty) {
        task.logger.info("Found: ${project.get().asFile.path}")
        task.logger.info("Execute: ${executable.get()}")
    }

}