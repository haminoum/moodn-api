package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
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
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@JdbcTest(properties = ["spring.profiles.active=postgres-test-container"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBUser::class, DBMood::class, DatabaseConfig::class)
@Transactional
@Tag("unit-test")
internal class DBMoodTest {

    @Autowired
    private lateinit var moodRepository: DBMood

    @Autowired
    private lateinit var userRepository: DBUser

    @Autowired
    private lateinit var database: Database

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)
    }

    @Test
    internal fun `should create and find mood`() {
        val user = User.fixture()
        val mood = Mood.fixture().copy(user = user.id)

        userRepository.create(user)
        moodRepository.createAll(listOf(mood), user.id)

        val actualMood = moodRepository.find(mood.id)!!

        assertThat(actualMood.id).isEqualTo(mood.id)
        assertThat(actualMood.type).isEqualTo(mood.type)
        assertThat(actualMood.user).isEqualTo(mood.user)
    }

    @Test
    internal fun `should update mood`() {
        val beforeNow = Instant.now().minusSeconds(200)
        val user = User.fixture()
        val mood = Mood.fixture().copy(user = user.id, createdAt = beforeNow, updatedAt = null)
        val comment = Comment.fixture().copy(author = user.id)
        userRepository.create(user)
        moodRepository.createAll(listOf(mood), mood.user)

        val updatedMood = moodRepository.updateMood(mood.id, comment)!!

        assertThat(updatedMood.updatedAt).isNotNull
        assertThat(updatedMood.updatedAt).isAfterOrEqualTo(mood.createdAt)
    }
}