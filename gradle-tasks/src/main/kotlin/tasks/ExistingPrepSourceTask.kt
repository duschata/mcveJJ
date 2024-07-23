package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class ExistingPrepSourceTask : DefaultTask() {

    @get:InputDirectory
    abstract val source: DirectoryProperty

    @get:OutputDirectory
    abstract val target: DirectoryProperty

    @TaskAction
    fun applyTask() {
        action(project, source, target)
    }

    companion object {
        fun action(project: Project, source: DirectoryProperty, target: DirectoryProperty) {
            project.logger.info("do very cool things with source: ${source.get().asFile.path} and target: ${target.get().asFile.path}")
        }
    }

}