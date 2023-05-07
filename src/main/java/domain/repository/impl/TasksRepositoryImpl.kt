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
import domain.repository.utils.intervalField
import domain.repository.utils.localDateTimeFrom
import domain.repository.utils.nowField
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class TasksRepositoryImpl @Autowired constructor(
    private val dslContext: DSLContext,
) : TasksRepository {
    override fun get(ids: Collection<TaskId>): List<Task> {
        return dslContext.selectFrom(TASKS)
            .where(TASKS.ID.`in`(ids.map(TaskId::value)))
            .fetch(::toModel)
    }

    override fun create(rqs: Collection<TaskCreateRq>): List<Task> {
        val (head, tail) = rqs
            .ifEmpty { return emptyList() }
            .run { first() to drop(1) }
        return tail.fold(
            initial = dslContext.insertInto(TASKS)
                .set(toRecord(head))
                .set(TASKS.SCHEDULED_TS, nowField().plus(intervalField(head.scheduleDelay)))
        ) { query, item ->
            query.newRecord()
                .set(toRecord(item))
                .set(TASKS.SCHEDULED_TS, nowField().plus(intervalField(item.scheduleDelay)))
        }.returning()
            .fetch()
            .map(::toModel)

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
                runnerName = runnerName,
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
                runnerName,
                Taskstatusenum.SCHEDULED,
                null,
                null,
                null,
            ).apply {
                reset(TASKS.ID)
                reset(TASKS.SCHEDULED_TS)
                reset(TASKS.CREATED_TS)
                reset(TASKS.UPDATED_TS)
            }
        }

        private fun toRecord(model: Task): TasksRecord = with(model) {
            TasksRecord(
                model.id.value,
                runnerName,
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