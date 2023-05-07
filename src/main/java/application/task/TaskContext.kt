package application.task

import domain.model.property.TaskProperty

data class TaskContext(
    val properties: List<TaskProperty>,
)
