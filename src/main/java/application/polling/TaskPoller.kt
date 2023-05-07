package application.polling

import application.processing.ProcessingResult
import application.processing.TaskProcessor
import application.utils.logger
import domain.model.task.TaskStatus
import domain.repository.TasksRepository
import domain.tx.TxHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextStartedEvent
import org.springframework.context.event.ContextStoppedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class TaskPoller @Autowired constructor(
    private val txHelper: TxHelper,

    private val taskProcessor: TaskProcessor,
    private val tasksRepository: TasksRepository,

    @Value("\${task.poller.thread.count:1}")
    private val threadCount: Int,
    @Value("\${task.poller.iteration.delay.millis:2000}")
    private val iterationDelayMillis: Long,
) {
    private val scheduledService = Executors.newScheduledThreadPool(threadCount)

    @EventListener(ContextStartedEvent::class)
    fun startPolling() {
        logger.info("Submitting polling tasks")
        repeat(threadCount) {
            scheduledService.scheduleWithFixedDelay(
                ::doBeforePause,
                iterationDelayMillis,
                iterationDelayMillis,
                TimeUnit.MILLISECONDS,
            )
        }
    }

    @EventListener(ContextStoppedEvent::class)
    fun endPolling() {
        logger.info("Ending polling on context stopped event")
        scheduledService.shutdownNow()
    }

    private fun doBeforePause() {
        var shouldContinue = true
        while (shouldContinue) {
            shouldContinue = shouldContinue(doPoll())
        }
    }

    private fun shouldContinue(result: PollingResult) = when (result) {
        is PollingResult.NoTasks -> false
        is PollingResult.Error -> false
        is PollingResult.Success -> true
    }

    private fun doPoll(): PollingResult {
        logger.info("Making polling iteration")
        return txHelper.withTx {
            val task = tasksRepository.getScheduled(limit = 1).firstOrNull()
                ?: run {
                    logger.debug("No scheduled tasks found")
                    return@withTx PollingResult.NoTasks
                }
            when (val processingResult = taskProcessor.process(task)) {
                is ProcessingResult.Error -> {
                    logger.error("Polling iteration failed")
                    tasksRepository.update(
                        task.copy(status = TaskStatus.FAILED),
                    )
                    PollingResult.Error(processingResult.error)
                }

                is ProcessingResult.Success -> {
                    logger.debug("Successful polling iteration")
                    tasksRepository.update(
                        task.copy(status = TaskStatus.COMPLETED),
                    )
                    PollingResult.Success
                }
            }
        }
    }

    companion object {
        private val logger = logger()
    }
}