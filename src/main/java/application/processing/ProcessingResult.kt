package application.processing

sealed interface ProcessingResult {
    object Success : ProcessingResult
    data class Error(val error: Throwable) : ProcessingResult
}