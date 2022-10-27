package com.hero.moodn.moodnapi

import com.hero.moodn.domain.api.Users
import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.infrastructure.database.cleanAllTables
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.time.Instant

@SpringBootTest(
    properties = [
        "spring.profiles.active=postgres-test-container",
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@Tag("integration-test")
class MoodIntegrationMoodGrpcService {

    @Autowired
    private lateinit var http: TestRestTemplate

    @Autowired
    private lateinit var userAPI: Users

    @Autowired
    private lateinit var database: Database

    @LocalServerPort
    private val port = 0
    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)
    }

    @Test
    internal fun `user creates a mood`() {
        val user = User(UserId(), "Lil Ham", Instant.now())
        val data = """
            {
            "type" : "GOOD",
            username: "${user.username}"
            }
        """.trimIndent()
//        /moods/create?type=${MOOD}&username=${USER}
        val response = http.postForEntity("http://localhost:$port/moods/create", data, String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
//        userAPI.create(user)
//        val savedMoods = database.from(MoodTable)
//            .select(MoodTable.columns)
//            .map { it ->
//                Mood(
//                    id = it[MoodTable.externalId]!!,
//                    type = it[MoodTable.type]!!,
//                    user = it[UserTable.externalId]!!,
//                    createdAt = it[MoodTable.createdAt]!!,
//                )
//            }
//
//        assertThat(savedMoods).containsExactlyInAnyOrder(goodMood)
//
//        val savedUsername = database.from(UserTable)
//            .select(UserTable.username)
//            .map {
//                it[UserTable.username]!!
//            }
//
//        assertThat(savedUsername).containsExactlyInAnyOrder(user.username)
    }
}
