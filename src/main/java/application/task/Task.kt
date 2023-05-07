package application.task

import com.fasterxml.jackson.databind.JsonNode

class Task private constructor(
    val runnerName: String,
    val properties: Map<String, JsonNode>,
) {
    class Builder(private val runnerName: String) {
        private val properties = mutableMapOf<String, JsonNode>()

        fun withProperty(
            fieldName: String,
            value: JsonNode,
        ): Builder = apply { properties[fieldName] = value }

        fun withProperties(
            fieldNameToValue: Map<String, JsonNode>,
        ): Builder = apply { properties.putAll(fieldNameToValue) }

        fun build(): Task = Task(
            runnerName = runnerName,
            properties = properties,
        )
    }
}
