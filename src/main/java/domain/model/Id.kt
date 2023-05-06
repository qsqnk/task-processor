package domain.model

interface Id {
    val value: Long
}

@JvmInline
value class TaskId(override val value: Long) : Id

@JvmInline
value class TaskPropertyId(override val value: Long) : Id