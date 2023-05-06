package domain.model.property

import com.fasterxml.jackson.databind.JsonNode
import domain.model.TaskId

data class TaskPropertyCreateRq(
    val taskId: TaskId,
    val fieldName: String,
    val value: JsonNode,
)
