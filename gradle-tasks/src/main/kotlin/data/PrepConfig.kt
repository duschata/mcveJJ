package tasks

import org.gradle.api.file.Directory
import org.gradle.internal.impldep.kotlinx.serialization.Serializable

@Serializable
data class PrepConfig(val naturalRepo: String, val path: Directory, val property: String)
