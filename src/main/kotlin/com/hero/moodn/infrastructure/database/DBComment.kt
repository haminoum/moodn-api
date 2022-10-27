package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.CommentRepository
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.springframework.stereotype.Component

@Component
class DBComment(val database: Database) : CommentRepository {

    override fun find(commentId: CommentId): Comment? =
        database.from(CommentTable)
            .innerJoin(MoodTable, on = CommentTable.mood eq MoodTable.id)
            .select(CommentTable.columns + MoodTable.externalId)
            .where(CommentTable.externalId eq commentId)
            .map(::toComment)
            .firstOrNull()

    override fun createOrUpdateComment(comment: Comment): Comment? {
        val moodDBKey = queryDBMoodKey(comment.mood)
        database.insert(
            CommentTable,
        ) {
            set(CommentTable.externalId, comment.id)
            set(CommentTable.mood, moodDBKey)
            set(CommentTable.content, comment.content)
            set(CommentTable.createdAt, comment.createdAt)
            set(CommentTable.updatedAt, comment.updatedAt)
        }

        return this.find(comment.id)
    }

    override fun delete(commentId: CommentId): Boolean {
        val count = database.delete(CommentTable) {
            return@delete it.externalId eq commentId
        }
        return count >= 1
    }

    private fun toMood(row: QueryRowSet) = Mood(
        id = row[MoodTable.externalId]!!,
        type = row[MoodTable.type]!!,
        user = row[UserTable.externalId]!!,
        createdAt = row[MoodTable.createdAt]!!,
        updatedAt = row[MoodTable.createdAt],
        comment = row[CommentTable.externalId],
    )

    private fun toComment(row: QueryRowSet) = Comment(
        id = row[CommentTable.externalId]!!,
        content = row[CommentTable.content]!!,
        mood = row[MoodTable.externalId]!!,
        createdAt = row[CommentTable.createdAt]!!,
        updatedAt = row[CommentTable.createdAt],
    )

    private fun queryDBUserKey(userId: UserId): Int {
        return database.from(UserTable)
            .select(UserTable.id)
            .where { UserTable.externalId eq userId }
            .map { it[UserTable.id]!! }
            .firstOrNull()
            ?: error("User $userId does not exist.")
    }

    private fun queryDBMoodKey(moodId: MoodId): Int {
        return database.from(MoodTable)
            .select(MoodTable.id)
            .where { MoodTable.externalId eq moodId }
            .map { it[MoodTable.id]!! }
            .firstOrNull()
            ?: error("Mood $moodId does not exist.")
    }
}
