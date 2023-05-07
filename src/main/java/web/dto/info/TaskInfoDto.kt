package web.dto.info

import com.fasterxml.jackson.annotation.JsonProperty

data class TaskInfoDto(
    @JsonProperty("taskStatus", required = true)
    val taskStatus: TaskStatusDto,
)
