package action

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory

interface EclipseProject {

    @get:InputDirectory
    val projectDirectory: DirectoryProperty
}