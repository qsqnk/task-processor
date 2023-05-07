package web.api

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import web.dto.SubmitTaskRqDto

@RequestMapping("/api/v1/task")
interface TaskController {
    @PostMapping(
        "/",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun submit(
        @RequestBody rq: SubmitTaskRqDto,
    ): ResponseEntity<Nothing?>
}