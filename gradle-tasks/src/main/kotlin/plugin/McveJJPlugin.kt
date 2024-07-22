package plugin

import data.GlobalConfig
import org.gradle.api.Project
import org.gradle.api.Plugin

class McveJJPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("global", GlobalConfig::class.java)
    }
}