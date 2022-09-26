package com.hero.moodn.config
/*

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    fun dataSourceProperties() = DataSourceProperties()

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    fun primaryDataSource(properties: DataSourceProperties): HikariDataSource {
        return properties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean
    @Primary
    fun primaryTransactionManager(@Qualifier("primaryDataSource") dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }
}
*/
