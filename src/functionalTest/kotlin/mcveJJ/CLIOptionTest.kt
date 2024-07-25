package mcveJJ.mcveJJ

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class CLIOptionTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    private fun getProjectDir(): File = tempFolder.root

    @Test
    fun `should call the cliArgsDirectly`() {

        getProjectDir().resolve("build.gradle.kts").writeText(
            """
            abstract class TaskWithCLIOption : DefaultTask() {
                
                @get:Optional
                @get:Option(option = "excludeTasks", description = "list of tasks to exclude")
                @get:Input
                abstract val excludeTasks: ListProperty<String>

                init {
                    excludeTasks.convention(listOf())
                }

                @TaskAction
                fun applyTask() {
                    logger.info("excludeTasks:" + excludeTasks.get())
                }
            }

            tasks.register<TaskWithCLIOption>("taskWithCLIOption")
        """.trimIndent()
        )

        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("-i", "-s", "taskWithCLIOption", "--excludeTasks=task1", "--excludeTasks=task2")
        runner.withProjectDir(getProjectDir())
        runner.build()
    }

    @Test
    fun `should call the cliArgsViaVararg`() {

        getProjectDir().resolve("build.gradle.kts").writeText(
            """
            abstract class TaskWithCLIOption : DefaultTask() {
                
                @get:Optional
                @get:Option(option = "excludeTasks", description = "list of tasks to exclude")
                @get:Input
                abstract val excludeTasks: ListProperty<String>

                init {
                    excludeTasks.convention(listOf())
                }

                @TaskAction
                fun applyTask() {
                    logger.info("excludeTasks:" + excludeTasks.get())
                }
            }

            tasks.register<TaskWithCLIOption>("taskWithCLIOption")
        """.trimIndent()
        )

        createGradleRunner(getProjectDir(), "--excludeTasks=task1", "--excludeTasks=task2")
    }

    private fun createGradleRunner(projectDir: File, vararg cliArgs: String) {

        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("-i", "-s", "taskWithCLIOption", *cliArgs)
        runner.withProjectDir(projectDir)
        runner.build()

    }

}