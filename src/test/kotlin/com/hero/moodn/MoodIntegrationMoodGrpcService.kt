package com.hero.moodn.moodnapi

import com.hero.moodn.domain.api.Users
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import com.hero.moodn.infrastructure.database.MoodTable
import com.hero.moodn.infrastructure.database.UserTable
import com.hero.moodn.infrastructure.database.cleanAllTables
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.time.Instant

@SpringBootTest(
    properties = [
        "spring.profiles.active=moodn-postgres-test-container",
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@Tag("integration-test")
class MoodIntegrationMoodGrpcService {

    @Autowired
    private lateinit var http: TestRestTemplate

    @Autowired
    private lateinit var moodRepository: MoodRepository

    @Autowired
    private lateinit var userAPI: Users

    @Autowired
    private lateinit var database: Database

    lateinit var goodMood: Mood
    lateinit var user: User

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)

        goodMood = Mood(type = MoodType.NEUTRAL)
        user = User(UserId(), "Lil Ham", Instant.now())
        userAPI.create(user)
        moodRepository.createAll(listOf(goodMood), UserId())
    }

    @Test
    internal fun `can create a mood`() {
        val data = """
            {
            type: "GOOD",
            username: "${user.username}"
            }
        """.trimIndent()
        val response = http.postForEntity("/moods/create", data, String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val savedMoods = database.from(MoodTable)
            .select(MoodTable.columns)
            .map {
                Mood(it[MoodTable.externalId]!!, it[MoodTable.type]!!, it[MoodTable.createdAt]!!)
            }

        assertThat(savedMoods).containsExactlyInAnyOrder(goodMood)

        val savedUsername = database.from(UserTable)
            .select(UserTable.username)
            .map {
                it[UserTable.username]!!
            }

        assertThat(savedUsername).containsExactlyInAnyOrder(user.username)
    }
}
