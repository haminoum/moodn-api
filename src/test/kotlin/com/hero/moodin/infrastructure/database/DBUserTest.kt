package com.hero.moodin.infrastructure.database

import com.hero.moodin.domain.model.User
import fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import java.time.Instant

@JdbcTest(properties = ["spring.profiles.active=moodin-postgres-test-container"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DBUser::class, KtormConfiguration::class)
internal class DBUserTest {
    @Autowired
    private lateinit var repository: DBUser

    @Autowired
    private lateinit var database: Database

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)
    }

    @Test
    internal fun `should create and find user`() {
        val user = User.fixture()
        repository.create(user)

        val actualUser = repository.find(user.username)

        assertThat(actualUser).isEqualTo(user)
    }

    @Test
    internal fun `should create multiple users at once`() {
        val user = User.fixture()
        val anotherUser = User.fixture().copy(username = "Big Ham")
        val yetAnotherUser = User.fixture().copy(username = "Lil Ham")

        repository.createOrUpdateAll(setOf(anotherUser, user, yetAnotherUser))

        assertThat(repository.find(user.username)).isEqualTo(user)
        assertThat(repository.find(anotherUser.username)).isEqualTo(anotherUser)
    }

    @Test
    internal fun `should update a user`() {
        val now = Instant.now()
        val user = User.fixture().copy(createdAt = now.minusSeconds(500))
        repository.create(user)

        val updatedUser = user.copy(createdAt = now)
        repository.createOrUpdateAll(setOf(updatedUser))

        val userFromDB = repository.find(user.username)!!

        assertThat(userFromDB.createdAt).isEqualTo(now)
    }
}
