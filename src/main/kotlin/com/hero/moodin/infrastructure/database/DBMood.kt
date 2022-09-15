import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodId
import com.hero.moodin.domain.spi.MoodSPI
import com.hero.moodin.infrastructure.database.MoodTable
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.springframework.stereotype.Component

@Component
class DBMood(val database: Database) : MoodSPI {

    override fun create(mood: Mood): Mood {
        database.insert(MoodTable) {
            set(it.externalId, mood.id)
            set(it.type, mood.type)
            set(it.createdAt, mood.createdAt)
        }
        return mood
    }

    override fun find(id: MoodId): Mood? =
        database.from(MoodTable)
            .select(MoodTable.columns)
            .where(MoodTable.externalId eq id)
            .map(::toFeeling)
            .firstOrNull()
}

private fun toFeeling(row: QueryRowSet) = Mood(
    id = row[MoodTable.externalId]!!,
    type = row[MoodTable.type]!!,
    createdAt = row[MoodTable.createdAt]!!,
)
