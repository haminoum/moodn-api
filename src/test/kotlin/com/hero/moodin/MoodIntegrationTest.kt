package com.hero.moodin

import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodType
import com.hero.moodin.domain.api.MoodsAPI
import com.hero.moodin.domain.api.UsersAPI
import com.hero.moodin.infrastructure.database.MoodTable
import com.hero.moodin.infrastructure.database.UserTable
import com.hero.moodin.infrastructure.database.cleanAllTables
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(
    properties = [
        "spring.profiles.active=moodin-postgres-test-container",
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class MoodIntegrationTest {

    @Autowired
    private lateinit var http: TestRestTemplate

    @Autowired
    private lateinit var moodsAPI: MoodsAPI

    @Autowired
    private lateinit var userAPI: UsersAPI

    @Autowired
    private lateinit var database: Database

    lateinit var goodMood: Mood
    lateinit var username: String

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)

        goodMood = Mood(type = MoodType.NEUTRAL)
        username = "Lil Ham"
        userAPI.create(username)
        moodsAPI.create(goodMood, username)
    }

    @Test
    internal fun `can create a mood`() {
        val data = """
            {
            type: "GOOD",
            username: "$username"
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

        assertThat(savedUsername).containsExactlyInAnyOrder(username)
    }
}