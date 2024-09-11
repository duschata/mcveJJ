package tasks

import data.InputResource
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TaskWithInputList : DefaultTask() {

    @get:Inject
    abstract val objectFactory: ObjectFactory

    @get:Nested
    abstract val inputList: ListProperty<InputResource>

    fun inputList(action: Action<InputResource>) {
        val inputResource = objectFactory.newInstance(InputResource::class.java)
        action.execute(inputResource)
    }

    @TaskAction
    fun apply() {

        val texts = inputList.get().map { it.input.file("aFile.txt").get().asFile.readText() }.joinToString { it }
        logger.info(texts)
    }
}
