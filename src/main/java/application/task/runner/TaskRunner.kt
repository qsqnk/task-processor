package application.task.runner

import application.task.TaskContext

interface TaskRunner {
    val name: String

    fun run(context: TaskContext)
}