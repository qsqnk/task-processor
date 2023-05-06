package domain.repository.utils

import org.jooq.Condition
import org.jooq.TableField
import org.jooq.impl.DSL.trueCondition

sealed interface SelectFilter<out T> {
    object All : SelectFilter<Nothing>
    data class SomeOf<T>(val values: Collection<T>) : SelectFilter<T>
}

fun <T> TableField<*, T>.satisfies(filter: SelectFilter<T>): Condition = when (filter) {
    is SelectFilter.All -> trueCondition()
    is SelectFilter.SomeOf -> `in`(filter.values)
}