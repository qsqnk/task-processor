package domain.repository

import domain.model.TaskId
import domain.model.property.TaskProperty
import domain.model.property.TaskPropertyCreateRq
import domain.repository.utils.SelectFilter

interface TasksPropertiesRepository {
    fun create(rqs: Collection<TaskPropertyCreateRq>)
    fun get(taskId: TaskId, fieldNameFilter: SelectFilter<String>): List<TaskProperty>
}