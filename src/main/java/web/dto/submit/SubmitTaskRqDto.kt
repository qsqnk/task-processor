package web.dto.submit

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

data class SubmitTaskRqDto(
    @JsonProperty("runnerName", required = true)
    val runnerName: String,

    @JsonProperty("properties", required = false)
    val properties: Map<String, JsonNode>?,
)
