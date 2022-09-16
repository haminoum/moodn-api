import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodId
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.MoodSPI
import com.hero.moodn.infrastructure.database.MoodTable
import com.hero.moodn.infrastructure.database.UserTable
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.postgresql.bulkInsert
import org.springframework.stereotype.Component

@Component
class DBMood(val database: Database) : MoodSPI {

    override fun create(mood: Mood, userId: UserId): Mood {
        createAll(setOf(mood), userId)
        return mood
    }

    override fun find(id: MoodId): Mood? =
        database.from(MoodTable).select(MoodTable.columns).where(MoodTable.externalId eq id).map(::toMood).firstOrNull()

    override fun createAll(moods: Set<Mood>, id: UserId) {
        val userDBKey = database.from(UserTable)
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
        }
    }
}

private fun toMood(row: QueryRowSet) = Mood(
    id = row[MoodTable.externalId]!!,
    type = row[MoodTable.type]!!,
    createdAt = row[MoodTable.createdAt]!!,
)
