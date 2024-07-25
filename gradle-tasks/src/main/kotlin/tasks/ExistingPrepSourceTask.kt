package tasks

import action.PrepSourceAction
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ExistingPrepSourceTask : DefaultTask() {

    @get:Inject
    protected abstract val objects: ObjectFactory

    @get:InputDirectory
    abstract val source: DirectoryProperty

    @get:OutputDirectory
    abstract val target: DirectoryProperty


    @TaskAction
    fun applyTask() {
        val prepSourceAction = objects.newInstance(PrepSourceAction::class.java)
        prepSourceAction.action(this, source, target)
    }
}