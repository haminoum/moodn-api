package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.User
import fixture
import org.assertj.core.api.Assertions
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
@Import(DBUser::class, DBComment::class, DBMood::class, DatabaseConfig::class)
@Transactional
@Tag("unit-test")
internal class DBCommentTest {

    @Autowired
    private lateinit var commentRepository: DBComment

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
    internal fun `should delete a comment`() {
        val user = User.fixture()
        userRepository.create(user)
        val mood = Mood.fixture().copy(type = MoodType.SAD)
        moodRepository.createAll(moods = listOf(mood), userId = user.id)
        val comment = Comment.fixture().copy(mood = mood.id)
        commentRepository.createOrUpdateComment(comment)

        val deleted = commentRepository.delete(commentId = comment.id)

        val dbMood = moodRepository.find(mood.id)!!

        assertThat(deleted).isEqualTo(true)
        assertThat(dbMood.comment).isNull()
    }

    @Test
    internal fun `should create a comment`() {
        val user = User.fixture()
        userRepository.create(user)
        val mood = Mood.fixture().copy(type = MoodType.SAD)
        moodRepository.createAll(moods = listOf(mood), userId = user.id)
        var moodFromDB = moodRepository.find(mood.id)!!
        Assertions.assertThat(moodFromDB.comment).isNull()

        val comment = Comment.fixture().copy(mood = mood.id)
        commentRepository.createOrUpdateComment(comment)

        val commentFromDB = commentRepository.find(comment.id)!!
        assertThat(commentFromDB.mood).isEqualTo(comment.mood)
        assertThat(commentFromDB.content).isEqualTo(comment.content)
    }

    @Test
    internal fun `should update comment`() {
        val user = User.fixture()
        userRepository.create(user)

        val beforeNow = Instant.now().minusSeconds(200)
        val mood = Mood.fixture().copy(user = user.id, createdAt = beforeNow, updatedAt = null)
        moodRepository.createAll(listOf(mood), mood.user)

        val comment = Comment.fixture().copy(mood = mood.id)

        val updatedMood = commentRepository.createOrUpdateComment(comment)!!

        assertThat(updatedMood.updatedAt).isNotNull
        assertThat(updatedMood.updatedAt).isAfterOrEqualTo(mood.createdAt)
    }
}
