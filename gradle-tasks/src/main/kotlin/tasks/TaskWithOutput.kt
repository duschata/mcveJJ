package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.util.*

abstract class TaskWithOutput : DefaultTask() {

    @get:Input
    abstract val input: Property<String>

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @TaskAction
    fun apply() {
        output.get().file("aFile.txt").asFile.writeText("${input.get()}:${Date().time.toString()}")
    }
}