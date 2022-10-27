package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Mood
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
import org.springframework.transaction.annotation.Transactional

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
    internal fun `should update mood's type`() {
        val user = User.fixture()
        userRepository.create(user)

        val mood = Mood.fixture().copy(type = MoodType.SAD)
        moodRepository.createAll(moods = listOf(mood), userId = user.id)

        val moodUpdated = MoodType.HAPPY
        val updatedMood = moodRepository.updateType(moodId = mood.id, moodUpdated)!!

        assertThat(updatedMood.type).isEqualTo(moodUpdated)
    }

    @Test
    internal fun `should delete mood`() {
        val user = User.fixture()
        userRepository.create(user)

        val mood = Mood.fixture().copy(type = MoodType.SAD)
        moodRepository.createAll(moods = listOf(mood), userId = user.id)

        val deleted = moodRepository.delete(moodId = mood.id)

        val dbMood = moodRepository.find(mood.id)

        assertThat(deleted).isEqualTo(true)
        assertThat(dbMood).isNull()
    }
}
