package action

import org.gradle.api.Task
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class PrepSourceAction {

    @get:Inject
    abstract val execOperations: ExecOperations

    @get:Inject
    abstract val fileSystemOperations: FileSystemOperations

    fun action(task: Task, path: DirectoryProperty, prepOut: DirectoryProperty) {

        task.logger.info("do very generic reusable things here")

        execOperations.exec {
            commandLine("echo", "Hello Jendrik")
        }
        fileSystemOperations.copy {
            from(path)
            into(prepOut)
        }
    }
}