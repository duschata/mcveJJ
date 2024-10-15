package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

abstract class CopyTaskConsumer : DefaultTask() {

    @get:InputFiles
    abstract val projectFiles: ListProperty<Directory>

    @TaskAction
    fun appy() {
        logger.info("run task because input has changed")
    }
}