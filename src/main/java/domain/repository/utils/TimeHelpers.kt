package domain.repository.utils

import org.jooq.Field
import org.jooq.impl.DSL.field
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.time.Duration

fun nowField(): Field<LocalDateTime> =
    field("now() at time zone 'UTC'", LocalDateTime::class.java)

fun intervalField(duration: Duration): Field<*> =
    field("interval '${duration.inWholeMilliseconds} milliseconds'")

fun instantFrom(localDateTime: LocalDateTime): Instant =
    localDateTime.toInstant(ZoneOffset.UTC)

fun localDateTimeFrom(instant: Instant): LocalDateTime =
    LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
