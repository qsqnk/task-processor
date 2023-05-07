package web.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import web.dto.info.TaskInfoDto
import web.dto.submit.SubmitTaskRqDto
import web.dto.submit.SubmitTaskRsDto

@RequestMapping("/api/v1/task")
interface TaskController {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun submit(
        @RequestBody rq: SubmitTaskRqDto,
    ): SubmitTaskRsDto

    @GetMapping(
        "/{taskId}/info",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun info(
        @PathVariable("taskId") taskId: Long,
    ): TaskInfoDto
}