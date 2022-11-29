// package com.hero.moodn.infrastructure.database
// TODO
// import com.hero.moodn.domain.model.User
// import fixture
// import org.assertj.core.api.Assertions.assertThat
// import org.junit.Ignore
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Tag
// import org.junit.jupiter.api.Test
// import org.ktorm.database.Database
// import org.springframework.beans.factory.annotation.Autowired
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
// import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
// import org.springframework.context.annotation.Import
// import org.springframework.transaction.annotation.Transactional
// import java.time.Instant
//
// @JdbcTest(properties = ["spring.profiles.active=postgres-test-container"])
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @Import(DBUser::class, DatabaseConfig::class)
// @Transactional
// @Tag("unit-test")
// internal class DBUserTest {
//    @Autowired
//    private lateinit var repository: DBUser
//
//    @Autowired
//    private lateinit var database: Database
//
//    @BeforeEach
//    internal fun setUp() {
//        cleanAllTables(database)
//    }
//
//    @Test
//    @Ignore
//    internal fun `should create and find user`() {
//        val user = User.fixture()
//        repository.create(user)
//
//        val actualUser = repository.find(user.id)
//
//        assertThat(actualUser).isEqualTo(user)
//    }
//
//    @Test
//    @Ignore
//    internal fun `should create multiple users at once`() {
//        val user = User.fixture()
//        val anotherUser = User.fixture().copy(username = "Big Ham")
//        val yetAnotherUser = User.fixture().copy(username = "Lil Ham")
//        val users = setOf(user, anotherUser, yetAnotherUser)
//
//        repository.createOrUpdateAll(users)
//
//        assertThat(repository.find(user.id)).isEqualTo(user)
//        assertThat(repository.find(anotherUser.id)).isEqualTo(anotherUser)
//        assertThat(repository.find(yetAnotherUser.id)).isEqualTo(yetAnotherUser)
//    }
//
//    @Test
//    @Ignore
//    internal fun `should update a user`() {
//        val now = Instant.now()
//        val user = User.fixture().copy(createdAt = now.minusSeconds(500))
//        repository.create(user)
//
//        val updatedUser = user.copy(createdAt = now)
//        repository.createOrUpdateAll(setOf(updatedUser))
//
//        val userFromDB = repository.find(user.id)!!
//
//        assertThat(userFromDB.createdAt).isEqualTo(now)
//    }
// }
