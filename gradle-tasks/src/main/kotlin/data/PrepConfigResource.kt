package data

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.work.Incremental

interface PrepConfigResource {

    @get:Input
    val naturalRepo: Property<String>

    @get:InputDirectory
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    val path: DirectoryProperty

    @get:Input
    val property: Property<String>
}