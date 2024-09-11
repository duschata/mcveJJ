package tasks

import data.InputResource
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.Directory
import org.gradle.api.internal.file.collections.FileCollectionAdapter
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.internal.impldep.org.apache.commons.io.FileCleaningTracker
import org.gradle.work.Incremental
import javax.inject.Inject

abstract class TaskWithConfigurableFileCollection : DefaultTask() {


    @get:Inject
    abstract val objectFactory: ObjectFactory

    @get:InputFiles
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val inputDirectories: ConfigurableFileCollection


    @TaskAction
    fun apply() {
        val dirList: List<Directory> =
            //           TODO: ASK JJ, how to identify if the path is a directory and use it for further operations. This is a ride over the lake
            inputDirectories.from.filter { (it as FileCollectionAdapter).singleFile.isDirectory }.map {
                val dir = objectFactory.directoryProperty().dir((it as FileCollectionAdapter).singleFile.path)
                dir.get()
            }


    }
}
