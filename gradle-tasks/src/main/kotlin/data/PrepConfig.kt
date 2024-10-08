package data

import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import java.io.File
import javax.inject.Inject

abstract class PrepConfig {

    @get:Inject
    abstract val objects: ObjectFactory

    @get: Input
    abstract val configuration: ListProperty<File>

    @get:Optional
    @get:Input
    abstract val sth: Property<String>

    @get:OutputDirectory
    abstract val prepOut: DirectoryProperty

    @get:Nested
    abstract val prepConfigResources: ListProperty<PrepConfigResource>

    fun prepConfigResource(action: Action<PrepConfigResource>) {
        val natProjectResource = objects.newInstance(PrepConfigResource::class.java)
        action.execute(natProjectResource)
        prepConfigResources.add(natProjectResource)
    }

    override fun toString(): String {
        return "PrepConfig(" +
                "sth=${sth.get()}, " +
                "prepOut=${prepOut.get()}, " +
                "prepConfigResources=${prepConfigResources.get()}" +
                ")"
    }


}