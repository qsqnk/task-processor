package web.api.conversions

import application.task.DbTask
import application.task.Task
import domain.model.task.TaskStatus
import web.dto.info.TaskInfoDto
import web.dto.info.TaskStatusDto
import web.dto.submit.SubmitTaskRqDto

fun SubmitTaskRqDto.toModel(): Task =
    Task.Builder(runnerName)
        .withProperties(properties.orEmpty())
        .build()

fun DbTask.toDto(): TaskInfoDto =
    TaskInfoDto(
        taskStatus = status.toDto(),
    )

fun TaskStatus.toDto(): TaskStatusDto = when (this) {
    TaskStatus.SCHEDULED -> TaskStatusDto.SCHEDULED
    TaskStatus.COMPLETED -> TaskStatusDto.COMPLETED
    TaskStatus.FAILED -> TaskStatusDto.FAILED
}