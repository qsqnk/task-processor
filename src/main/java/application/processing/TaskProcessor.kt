package application.processing

import application.task.TaskContext
import application.task.runner.TaskRunner
import application.utils.logger
import domain.model.task.Task
import domain.repository.TasksPropertiesRepository
import domain.repository.utils.SelectFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TaskProcessor @Autowired constructor(
    private val runners: List<TaskRunner>,
    private val tasksPropertiesRepository: TasksPropertiesRepository,
) {
    fun process(task: Task): ProcessingResult {
        logger.info(
            "Processing task with runnerName={}",
            task.runnerName,
        )
        return runCatching {
            val runner = findByName(task.runnerName)
            val properties = tasksPropertiesRepository.get(
                taskId = task.id,
                fieldNameFilter = SelectFilter.All,
            )
            val context = TaskContext(
                properties = properties,
            )
            runner.run(context)
        }.fold(
            onFailure = { throwable ->
                logger.error(
                    "Got error while processing task with runnerName={}", task.runnerName,
                    throwable,
                )
                ProcessingResult.Error(throwable)
            },
            onSuccess = {
                logger.debug(
                    "Successfully processed task with runnerName={}",
                    task.runnerName,
                )
                ProcessingResult.Success
            },
        )
    }

    private fun findByName(runnerName: String): TaskRunner =
        runners.firstOrNull { it.name == runnerName }
            ?: throw NoSuchElementException("No runner with name $runnerName found")

    companion object {
        private val logger = logger()
    }
}