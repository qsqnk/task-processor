package web.api.impl

import application.task.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import web.api.TaskController
import web.api.conversions.toModel
import web.dto.SubmitTaskRqDto
import kotlin.time.Duration

@RestController
class TaskControllerImpl @Autowired constructor(
    private val taskService: TaskService,
) : TaskController {
    override fun submit(rq: SubmitTaskRqDto): ResponseEntity<Nothing?> {
        taskService.submit(
            task = rq.toModel(),
            delay = Duration.ZERO,
        )
        return ResponseEntity.ok(null)
    }
}