package data

import org.gradle.api.file.Directory
//import java.io.Serializable

import kotlinx.serialization.Serializable

@Serializable
data class PrepConfig(val naturalRepo: String, val path: Directory, val property: String)
