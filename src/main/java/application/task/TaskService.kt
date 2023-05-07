package application.task

import application.utils.logger
import domain.model.TaskId
import domain.model.property.TaskPropertyCreateRq
import domain.model.task.TaskCreateRq
import domain.repository.TasksPropertiesRepository
import domain.repository.TasksRepository
import domain.tx.TxHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.time.Duration

typealias DbTask = domain.model.task.Task

@Service
class TaskService @Autowired constructor(
    private val txHelper: TxHelper,
    private val tasksRepository: TasksRepository,
    private val tasksPropertiesRepository: TasksPropertiesRepository,
) {
    fun get(taskId: TaskId): DbTask {
        return tasksRepository.get(taskId)
            ?: throw NoSuchElementException("Task with id=$taskId not found")
    }

    fun submit(task: Task, delay: Duration = Duration.ZERO): TaskId {
        logger.info(
            "Submitting task with runnerName={} with delay={}",
            task.runnerName,
            delay,
        )
        return txHelper.withTx {
            val createdTask = tasksRepository.create(
                TaskCreateRq(task.runnerName, delay),
            )
            val taskPropertyCreateRqs = task.properties.map { (fieldName, value) ->
                TaskPropertyCreateRq(
                    taskId = createdTask.id,
                    fieldName = fieldName,
                    value = value,
                )
            }
            tasksPropertiesRepository.create(taskPropertyCreateRqs)
            createdTask.id
        }
    }

    companion object {
        private val logger = logger()
    }
}