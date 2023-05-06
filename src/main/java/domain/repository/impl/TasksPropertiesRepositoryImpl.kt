package domain.repository.impl

import com.fasterxml.jackson.databind.ObjectMapper
import domain.db.Tables.TASKS_PROPERTIES
import domain.db.tables.records.TasksPropertiesRecord
import domain.model.TaskId
import domain.model.TaskPropertyId
import domain.model.property.TaskProperty
import domain.model.property.TaskPropertyCreateRq
import domain.repository.TasksPropertiesRepository
import domain.repository.utils.SelectFilter
import domain.repository.utils.satisfies
import org.jooq.DSLContext
import org.jooq.JSONB
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class TasksPropertiesRepositoryImpl @Autowired constructor(
    private val dslContext: DSLContext,
    private val objectMapper: ObjectMapper,
) : TasksPropertiesRepository {
    override fun create(rqs: Collection<TaskPropertyCreateRq>) {
        rqs.map(::toRecord)
            .let(dslContext::batchInsert)
            .execute()
    }

    override fun get(taskId: TaskId, fieldNameFilter: SelectFilter<String>): List<TaskProperty> {
        return dslContext.selectFrom(TASKS_PROPERTIES)
            .where(TASKS_PROPERTIES.TASK_ID.eq(taskId.value))
            .and(TASKS_PROPERTIES.FIELD_NAME.satisfies(fieldNameFilter))
            .fetch(::toModel)
    }

    private fun toModel(record: TasksPropertiesRecord): TaskProperty = with(record) {
        TaskProperty(
            id = TaskPropertyId(id),
            taskId = TaskId(taskId),
            fieldName = fieldName,
            value = objectMapper.readTree(value.data()),
        )
    }

    private fun toRecord(model: TaskPropertyCreateRq): TasksPropertiesRecord = with(model) {
        TasksPropertiesRecord(
            null,
            taskId.value,
            fieldName,
            objectMapper.writeValueAsString(value).let(JSONB::valueOf),
        ).apply {
            reset(TASKS_PROPERTIES.ID)
        }
    }
}