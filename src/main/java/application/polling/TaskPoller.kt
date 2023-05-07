package application.polling

import application.processing.ProcessingResult
import application.processing.TaskProcessor
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
    @Value("\${task.poller.iteration.delay.millis:500}")
    private val iterationDelayMillis: Long,
) {
    private val scheduledService = Executors.newScheduledThreadPool(threadCount)

    @EventListener(ContextStartedEvent::class)
    fun startPolling() {
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
        return txHelper.withTx {
            val task = tasksRepository.getScheduled(limit = 1)
                .firstOrNull() ?: return@withTx PollingResult.NoTasks
            when (val processingResult = taskProcessor.process(task)) {
                is ProcessingResult.Error -> {
                    tasksRepository.update(
                        task.copy(status = TaskStatus.FAILED),
                    )
                    PollingResult.Error(processingResult.error)
                }

                is ProcessingResult.Success -> {
                    tasksRepository.update(
                        task.copy(status = TaskStatus.COMPLETED),
                    )
                    PollingResult.Success
                }
            }
        }
    }
}