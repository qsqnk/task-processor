package web.api.impl

import application.task.TaskService
import domain.model.TaskId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import web.api.TaskController
import web.api.conversions.toDto
import web.api.conversions.toModel
import web.dto.info.TaskInfoDto
import web.dto.submit.SubmitTaskRqDto
import web.dto.submit.SubmitTaskRsDto
import kotlin.time.Duration

@RestController
class TaskControllerImpl @Autowired constructor(
    private val taskService: TaskService,
) : TaskController {
    override fun submit(rq: SubmitTaskRqDto): SubmitTaskRsDto {
        val taskId = taskService.submit(
            task = rq.toModel(),
            delay = Duration.ZERO,
        )
        return SubmitTaskRsDto(
            taskId = taskId.value,
        )
    }

    override fun info(taskId: Long): TaskInfoDto {
        return TaskId(taskId).let(taskService::get).toDto()
    }
}