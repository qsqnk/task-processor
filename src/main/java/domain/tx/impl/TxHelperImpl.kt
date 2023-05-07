package domain.tx.impl

import domain.tx.TxHelper
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.support.TransactionTemplate

class TxHelperImpl(
    private val transactionManager: PlatformTransactionManager,
) : TxHelper {
    override fun <T> withTx(
        propagation: Propagation,
        isolation: Isolation,
        readOnly: Boolean,
        supplier: () -> T,
    ): T {
        return TransactionTemplate(transactionManager)
            .apply {
                propagationBehavior = propagation.value()
                isolationLevel = isolation.value()
                isReadOnly = readOnly
            }.execute {
                supplier()
            }.let {
                checkNotNull(it) { "Transaction returned null" }
            }
    }
}