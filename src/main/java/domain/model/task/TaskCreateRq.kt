package domain.model.task

import kotlin.time.Duration

data class TaskCreateRq(
    val runnerName: String,
    val scheduleDelay: Duration,
)
