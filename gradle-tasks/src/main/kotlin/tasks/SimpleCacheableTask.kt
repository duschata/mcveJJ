package tasks

import data.PrepConfigResource
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.*
import org.gradle.work.InputChanges
import javax.inject.Inject

@CacheableTask
abstract class SimpleCacheableTask : DefaultTask() {

    @get:Inject
    protected abstract val objects: ObjectFactory

    @get:OutputDirectory
    abstract val outDir: DirectoryProperty

    @get:Nested
    abstract val natProjectResources: ListProperty<PrepConfigResource>


    fun natProjectResource(action: Action<PrepConfigResource>) {
        val natProjectResource = objects.newInstance(PrepConfigResource::class.java)
        action.execute(natProjectResource)
        natProjectResources.add(natProjectResource)
    }

    init {
        outDir.convention(project.layout.buildDirectory.dir("tmp"))
    }

    @TaskAction
    fun generate(inputs: InputChanges) {
        natProjectResources.get().forEach {
            logger.info("assigned natsource: ${it.naturalRepo.get()}, ${it.path.get().asFile.path}, ${it.property.get()}")
        }
        val natSourcesAsFileCollection = objects.fileCollection().from(natProjectResources.get().map { it.path.get() })

        if (inputs.isIncremental) {
            println("CHANGED: " + inputs.getFileChanges(natSourcesAsFileCollection))
            inputs.getFileChanges(natSourcesAsFileCollection).forEach {
                outDir.file("output.txt").get().asFile.appendText("\nout - " + it.file.name)
            }
        } else {
            println("FULL")
            natSourcesAsFileCollection.asFileTree.files.forEach {
                outDir.file("output.txt").get().asFile.appendText("\nout - " + it.name)
            }
        }
    }
}