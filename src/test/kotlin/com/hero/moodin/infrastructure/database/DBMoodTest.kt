package com.hero.moodin.infrastructure.database

import DBMood
import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodId
import com.hero.feelin.domain.model.MoodType
import com.hero.feelin.domain.model.User
import fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import

@JdbcTest(properties = ["spring.profiles.active=moodin-postgres-test-container"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBMood::class, KtormConfiguration::class)
internal class DBMoodTest {
    @Autowired
    private lateinit var repository: DBMood

    @Autowired
    private lateinit var database: Database

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)
    }

    @Test
    internal fun `should create and find mood`() {
        val mood = Mood(MoodId(), type = MoodType.HAPPY)
        val user = User.fixture()
        repository.create(mood, user.id)

        val actualMood = repository.find(mood.id)

        assertThat(actualMood).isEqualTo(mood)
    }
}
