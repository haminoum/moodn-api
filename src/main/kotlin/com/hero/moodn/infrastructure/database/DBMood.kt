package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.ktorm.support.postgresql.bulkInsert
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class DBMood(val database: Database) : MoodRepository {

    override fun find(moodId: MoodId): Mood? =
        database.from(MoodTable)
            .innerJoin(UserTable, on = MoodTable.user eq UserTable.id)
            .select(MoodTable.columns + UserTable.externalId)
            .where(MoodTable.externalId eq moodId)
            .map(::toMood)
            .firstOrNull()

    override fun createAll(moods: List<Mood>, userId: UserId) {
        val userDBKey = queryDBUserKey(userId)

        database.bulkInsert(MoodTable) {
            moods.forEach { mood ->
                item {
                    set(it.externalId, mood.id)
                    set(it.type, mood.type)
                    set(it.createdAt, mood.createdAt)
                    set(MoodTable.user, userDBKey)
                }
            }
        }
    }

    override fun update(moodId: MoodId, comment: Comment): Mood? {
        val authorDBKey = queryDBUserKey(comment.author)
        val commentDBKey = database.insertAndGenerateKey(
            CommentTable,
        ) {
            set(CommentTable.externalId, comment.id)
            set(CommentTable.author, authorDBKey)
            set(CommentTable.content, comment.content)
            set(CommentTable.createdAt, comment.createdAt)
            set(CommentTable.updatedAt, comment.updatedAt)
        } as Int

        database.update(MoodTable) {
            set(it.updatedAt, Instant.now())
            set(it.comment, commentDBKey)

            where { MoodTable.externalId eq moodId }
        }
        return this.find(moodId)
    }

    override fun update(moodId: MoodId, moodType: MoodType): Mood? {
        database.update(MoodTable) {
            set(MoodTable.type, moodType)
            where { MoodTable.externalId eq moodId }
        }
        return this.find(moodId)
    }

    private fun toMood(row: QueryRowSet) = Mood(
        id = row[MoodTable.externalId]!!,
        type = row[MoodTable.type]!!,
        user = row[UserTable.externalId]!!,
        createdAt = row[MoodTable.createdAt]!!,
        updatedAt = row[MoodTable.createdAt],
    )

    private fun queryDBUserKey(userId: UserId): Int {
        return database.from(UserTable)
            .select(UserTable.id)
            .where { UserTable.externalId eq userId }
            .map { it[UserTable.id]!! }
            .firstOrNull()
            ?: error("Could not create moods because User $userId does not exist.")
    }
}
