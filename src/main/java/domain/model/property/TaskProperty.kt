package domain.model.property

import com.fasterxml.jackson.databind.JsonNode
import domain.model.TaskId
import domain.model.TaskPropertyId

data class TaskProperty(
    val id: TaskPropertyId,
    val taskId: TaskId,
    val fieldName: String,
    val value: JsonNode,
)
