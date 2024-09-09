package tasks

import action.ExecuteBulkAction
import action.EclipseProject
import data.PrepConfig
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class ExecuteBulkTask : DefaultTask() {


    @get:Inject
    abstract val objects: ObjectFactory

    @get:Input
    abstract val executable: Property<File>

    @get:Input
    abstract val projectPaths: ListProperty<DirectoryProperty>

    @get:Nested
    abstract val eclipseProjects: ListProperty<EclipseProject>

    fun eclipseProject (action: Action<EclipseProject>) {
       val projectDir = objects.newInstance(EclipseProject::class.java)
        action.execute(projectDir)
        eclipseProjects.add(projectDir)
    }



    @TaskAction
    fun action() {

        logger.info("projectPaths.get().size: ${projectPaths.get().size}")
        eclipseProjects.get().forEach {
            objects.newInstance(ExecuteBulkAction::class.java).apply(this, executable, it.projectDirectory)
        }
    }
}