package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.Mood
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodRepository
import org.springframework.stereotype.Component

@Component
class DBMood : MoodRepository {

    override fun create(mood: Mood, userId: UserId): Mood {
        createAll(listOf(mood), userId)
        return mood
    }

    override fun find(id: MoodId): Mood? {
        return TODO()
    }
    // database.from(MoodTable).select(MoodTable.columns).where(MoodTable.externalId eq id).map(::toMood).firstOrNull()

    override fun createAll(moods: List<Mood>, id: UserId) {
        return TODO()
        /*val userDBKey = database.from(UserTable)
            .select(UserTable.id)
            .where { UserTable.externalId eq id }
            .map { it[UserTable.id]!! }
            .firstOrNull()
            ?: error("Could not create moods because User $id does not exist.")

        database.bulkInsert(MoodTable) {
            moods.forEach { mood ->
                item {
                    set(it.externalId, mood.id)
                    set(it.type, mood.type)
                    set(it.createdAt, mood.createdAt)
                    set(MoodTable.user, userDBKey)
                }
            }
        }*/
    }
}

// private fun toMood(row: QueryRowSet) = Mood(
//    id = row[MoodTable.externalId]!!,
//    type = row[MoodTable.type]!!,
//    createdAt = row[MoodTable.createdAt]!!,
// )
