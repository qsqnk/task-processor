package domain.repository

import domain.model.TaskId
import domain.model.task.Task
import domain.model.task.TaskCreateRq

interface TasksRepository {
    fun get(id: TaskId): Task? = get(listOf(id)).firstOrNull()
    fun get(ids: Collection<TaskId>): List<Task>
    fun create(rq: TaskCreateRq): Task = create(listOf(rq)).first()
    fun create(rqs: Collection<TaskCreateRq>): List<Task>
    fun update(task: Task) = update(listOf(task))
    fun update(tasks: Collection<Task>)
    fun getScheduled(limit: Long): List<Task>
}