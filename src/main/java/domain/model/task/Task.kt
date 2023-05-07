package domain.model.task

import domain.model.TaskId
import java.time.Instant

data class Task(
    val id: TaskId,
    val runnerName: String,
    val status: TaskStatus,
    val scheduledTs: Instant,
    val createdTs: Instant,
    val updatedTs: Instant,
)