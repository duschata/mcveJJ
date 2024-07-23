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

interface PrepConfigResource {

    @get:Input
    val repoName: Property<String>

    @get:InputDirectory
    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    val path: DirectoryProperty

    @get:Optional
    @get:OutputDirectory
    val bflex: DirectoryProperty

    @get:Input
    val additionalProperty: Property<String>
}