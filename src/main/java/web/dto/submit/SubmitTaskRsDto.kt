package web.dto.submit

import com.fasterxml.jackson.annotation.JsonProperty

data class SubmitTaskRsDto(
    @JsonProperty("taskId", required = true)
    val taskId: Long,
)
