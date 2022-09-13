package com.hero.feelinapi.infrastructure.database

import DBFeeling
import com.hero.feelin.domain.model.Feeling
import com.hero.feelin.domain.model.FeelingId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional

@JdbcTest(properties = ["spring.profiles.active=feelin-postgres-test-container"])
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBFeeling::class, KtormConfiguration::class)
internal class DBFeelingTest {
    @Autowired
    private lateinit var repository: DBFeeling

    @Autowired
    private lateinit var database: Database

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)
    }

    @Test
    internal fun `should create and find feeling`() {
        val feeling = Feeling(FeelingId())

        repository.create(feeling)

        val actualFeeling = repository.find(feeling.id)

        assertThat(actualFeeling).isEqualTo(feeling)
    }
}
