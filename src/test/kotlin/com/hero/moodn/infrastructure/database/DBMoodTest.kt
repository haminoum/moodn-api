package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.User
import fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import java.time.Instant

@JdbcTest(properties = ["spring.profiles.active=moodn-postgres-test-container"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBMood::class, DBUser::class, DatabaseConfig::class)
@Tag("unit-test")
internal class DBMoodTest {
    @Autowired
    private lateinit var repository: DBMood

    @Autowired
    private lateinit var users: DBUser

    @Autowired
    private lateinit var database: Database

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)
    }

    @Test
    internal fun `should create and find mood`() {
        val mood = Mood(MoodId(), type = MoodType.HAPPY)
        val user = User.fixture().copy(createdAt = Instant.now())
        users.create(user)

        repository.create(mood, userId = user.id)

        val actualMood = repository.find(mood.id)

        assertThat(actualMood).isEqualTo(mood)
    }
}
