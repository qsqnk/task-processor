package application.polling

sealed interface PollingResult {
    object NoTasks : PollingResult
    object Success : PollingResult
    data class Error(val error: Throwable) : PollingResult
}