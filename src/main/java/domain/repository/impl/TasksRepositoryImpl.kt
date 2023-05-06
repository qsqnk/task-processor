package domain.repository.impl

import domain.db.Tables.TASKS
import domain.db.enums.Taskstatusenum
import domain.db.tables.records.TasksRecord
import domain.model.TaskId
import domain.model.task.Task
import domain.model.task.TaskCreateRq
import domain.model.task.TaskStatus
import domain.repository.TasksRepository
import domain.repository.utils.instantFrom
import domain.repository.utils.localDateTimeFrom
import domain.repository.utils.nowField
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class TasksRepositoryImpl @Autowired constructor(
    private val dslContext: DSLContext,
) : TasksRepository {
    override fun create(rqs: Collection<TaskCreateRq>) {
        rqs.map(::toRecord)
            .let(dslContext::batchInsert)
            .execute()
    }

    override fun update(tasks: Collection<Task>) {
        tasks.map(::toRecord)
            .let(dslContext::batchUpdate)
            .execute()
    }

    override fun getScheduled(limit: Long): List<Task> {
        return dslContext.selectFrom(TASKS)
            .where(TASKS.STATUS.eq(Taskstatusenum.SCHEDULED))
            .and(TASKS.SCHEDULED_TS.lessThan(nowField()))
            .limit(limit)
            .forUpdate()
            .skipLocked()
            .fetch(::toModel)
    }

    companion object {
        private fun toModel(record: TasksRecord): Task = with(record) {
            Task(
                id = TaskId(id),
                name = name,
                status = toModel(status),
                scheduledTs = instantFrom(scheduledTs),
                createdTs = instantFrom(createdTs),
                updatedTs = instantFrom(updatedTs),
            )
        }

        private fun toModel(record: Taskstatusenum): TaskStatus = when (record) {
            Taskstatusenum.SCHEDULED -> TaskStatus.SCHEDULED
            Taskstatusenum.FAILED -> TaskStatus.FAILED
            Taskstatusenum.COMPLETED -> TaskStatus.COMPLETED
        }

        private fun toRecord(rq: TaskCreateRq): TasksRecord = with(rq) {
            TasksRecord(
                null,
                name,
                Taskstatusenum.SCHEDULED,
                scheduledTs?.let(::localDateTimeFrom),
                null,
                null,
            ).apply {
                reset(TASKS.ID)
                scheduledTs ?: reset(TASKS.SCHEDULED_TS)
                reset(TASKS.CREATED_TS)
                reset(TASKS.UPDATED_TS)
            }
        }

        private fun toRecord(model: Task): TasksRecord = with(model) {
            TasksRecord(
                model.id.value,
                name,
                toRecord(status),
                localDateTimeFrom(scheduledTs),
                localDateTimeFrom(createdTs),
                localDateTimeFrom(updatedTs),
            )
        }

        private fun toRecord(model: TaskStatus): Taskstatusenum = when (model) {
            TaskStatus.SCHEDULED -> Taskstatusenum.SCHEDULED
            TaskStatus.COMPLETED -> Taskstatusenum.COMPLETED
            TaskStatus.FAILED -> Taskstatusenum.FAILED
        }
    }
}