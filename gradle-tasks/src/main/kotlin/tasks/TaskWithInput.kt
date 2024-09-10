package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class TaskWithInput : DefaultTask() {

    @get:InputDirectory
    abstract val input: DirectoryProperty

    @TaskAction
    fun apply () {
        val readText = input.file("aFile.txt").get().asFile.readText()

        logger.info(readText)
    }
}
