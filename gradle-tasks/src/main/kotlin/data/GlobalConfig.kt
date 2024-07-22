package data

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

abstract class GlobalConfig {
    abstract val natSourcesPath:  DirectoryProperty
    abstract val natSourcesFirst: Property<String>
    abstract val natSourcesSecond: Property<String>
}