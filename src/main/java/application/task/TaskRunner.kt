package application.task

interface TaskRunner {
    val name: String

    fun run(context: TaskContext)
}