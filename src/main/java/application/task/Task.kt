package application.task

interface Task {
    val name: String

    fun run(context: TaskContext)
}