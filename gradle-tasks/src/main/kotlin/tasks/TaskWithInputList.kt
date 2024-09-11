package tasks

import data.InputResource
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.work.Incremental
import javax.inject.Inject

abstract class TaskWithInputList : DefaultTask() {

    @get:InputFiles
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val incrementalFiles: ConfigurableFileCollection

    @get:Inject
    abstract val objects: ObjectFactory

    @get:Nested
    abstract val inputList: ListProperty<InputResource>

    fun inputList(action: Action<InputResource>) {
        val inputResource = objects.newInstance(InputResource::class.java)
        action.execute(inputResource)
        incrementalFiles.from(inputResource.input)
        inputList.add(inputResource)
    }

    @TaskAction
    fun apply() {

        val texts = inputList.get().map { it.input.file("aFile.txt").get().asFile.readText() }.joinToString { it }
        logger.info(texts)
    }
}
