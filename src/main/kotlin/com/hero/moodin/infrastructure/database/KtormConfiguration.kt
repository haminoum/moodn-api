package com.hero.moodin.infrastructure.database

import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class KtormConfiguration {

    @Autowired
    @Bean
    fun database(dataSource: DataSource): Database {
        return Database.connectWithSpringSupport(dataSource)
    }
}
