package com.hero.moodin

import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodType
import com.hero.moodin.domain.api.FeelingsAPI
import com.hero.moodin.infrastructure.database.MoodTable
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
    private lateinit var feelingsAPI: FeelingsAPI

    @Autowired
    private lateinit var database: Database

    lateinit var goodMood: Mood

    @BeforeEach
    internal fun setUp() {
        cleanAllTables(database)

        goodMood = Mood(type = MoodType.NEUTRAL)
        feelingsAPI.create(goodMood)
    }

    @Test
    internal fun `can create a feeling`() {
        // upload the file
//        val response1 = http.postForEntity("/feelings", "", String::class.java)
//        assertThat(response1.statusCode).isEqualTo(HttpStatus.OK)
//
//        // uploading the file twice is idempotent
//        val response2 = http.postForEntity("/feelings", "", String::class.java)
//        assertThat(response2.statusCode).isEqualTo(HttpStatus.OK)
//
//        val actual = database.from(MoodTable)
//            .select(MoodTable.externalId)
//            .map {
//                Mood(it[MoodTable.externalId]!!)
//            }
//
//        assertThat(actual).containsExactlyInAnyOrder(Mood(id = goodMood.id))
//
//        val savedMoods = database.from(MoodTable)
//            .select(MoodTable.externalId)
//            .map {
//                Mood(id = goodMood.id)
//            }
//
//        assertThat(savedMoods).containsExactlyInAnyOrder(Mood(id = goodMood.id))
    }
}
