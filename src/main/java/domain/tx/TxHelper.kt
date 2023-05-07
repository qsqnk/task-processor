package domain.tx

import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation

interface TxHelper {
    fun <T> withTx(
        propagation: Propagation = Propagation.REQUIRED,
        isolation: Isolation = Isolation.READ_COMMITTED,
        readOnly: Boolean = false,
        supplier: () -> T,
    ): T
}