package web.api.conversions

import application.task.Task
import web.dto.SubmitTaskRqDto

fun SubmitTaskRqDto.toModel(): Task =
    Task.Builder(runnerName)
        .withProperties(properties.orEmpty())
        .build()