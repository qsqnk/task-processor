package application.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified C : Any> C.logger(): Logger =
    LoggerFactory.getLogger(
        if (C::class.isCompanion) C::class.java.enclosingClass else C::class.java
    )