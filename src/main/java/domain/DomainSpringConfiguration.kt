package domain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.tx.TxHelper
import domain.tx.impl.TxHelperImpl
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import org.springframework.transaction.PlatformTransactionManager

@SpringBootConfiguration
@ComponentScan(basePackageClasses = [DomainSpringConfiguration::class])
@Import(
    DataSourceAutoConfiguration::class,
    FlywayAutoConfiguration::class,
    JooqAutoConfiguration::class,
)
@EnableAutoConfiguration
class DomainSpringConfiguration {
    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper()
    }

    @Bean
    fun txHelper(transactionManager: PlatformTransactionManager): TxHelper {
        return TxHelperImpl(transactionManager)
    }
}