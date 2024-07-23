package data

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.work.Incremental

abstract class PrepConfigResource {

    @get:Input
    abstract val repoName: Property<String>

    @get:InputDirectory
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    abstract val path: DirectoryProperty

    @get:Optional
    @get:OutputDirectory
    abstract val bflex: DirectoryProperty

    @get:Input
    abstract val additionalProperty: Property<String>
    override fun toString(): String {
        return "PrepConfigResource(" +
                "repoName=${repoName.get()}, " +
                "path=${path.get()}, " +
                "bflex=" + if (bflex.isPresent) bflex.get() else " " + ", " +
                "additionalProperty=${additionalProperty.get()}" +
                ")"
    }
}