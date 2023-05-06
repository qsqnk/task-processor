package domain.model.task

import java.time.Instant

data class TaskCreateRq(
    val name: String,
    val scheduledTs: Instant?,
)
