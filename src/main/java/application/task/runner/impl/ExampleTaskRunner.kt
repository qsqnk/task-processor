package application.task.runner.impl

import application.task.TaskContext
import application.task.runner.TaskRunner
import application.utils.logger
import org.springframework.stereotype.Component

@Component
class ExampleTaskRunner : TaskRunner {
    override val name = RUNNER_NAME

    override fun run(context: TaskContext) {
        logger.info("Runner {} in action with context {}", name, context)
    }

    companion object {
        private val logger = logger()
        private const val RUNNER_NAME = "Example"
    }
}
