package domain.repository

import domain.model.task.Task
import domain.model.task.TaskCreateRq

interface TasksRepository {
    fun create(rqs: Collection<TaskCreateRq>)
    fun update(tasks: Collection<Task>)
    fun getScheduled(limit: Long): List<Task>
}