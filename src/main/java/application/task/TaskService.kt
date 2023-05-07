package application.task

import application.utils.logger
import domain.model.property.TaskPropertyCreateRq
import domain.model.task.TaskCreateRq
import domain.repository.TasksPropertiesRepository
import domain.repository.TasksRepository
import domain.tx.TxHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.time.Duration

@Service
class TaskService @Autowired constructor(
    private val txHelper: TxHelper,
    private val tasksRepository: TasksRepository,
    private val tasksPropertiesRepository: TasksPropertiesRepository,
) {
    fun submit(task: Task, delay: Duration = Duration.ZERO) {
        logger.info(
            "Submitting task with runnerName={} with delay={}",
            task.runnerName,
            delay,
        )
        txHelper.withTx {
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
        }
    }

    companion object {
        private val logger = logger()
    }
}