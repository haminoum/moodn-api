package com.hero.moodn.config

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DatabaseConfig {

    /**
     * Register the [Database] instance as a Spring bean.
     */
    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    fun database(): Database = Database.connectWithSpringSupport(dataSource)
}
