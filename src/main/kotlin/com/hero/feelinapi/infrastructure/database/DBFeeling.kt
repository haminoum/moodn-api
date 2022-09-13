import com.hero.feelin.domain.model.Feeling
import com.hero.feelin.domain.model.FeelingId
import com.hero.feelinapi.domain.spi.FeelingSPI
import com.hero.feelinapi.infrastructure.database.FeelingTable
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
class DBFeeling(val database: Database) : FeelingSPI {

    override fun create(feeling: Feeling): Feeling {
        database.insert(FeelingTable) {
            set(it.externalId, feeling.id)
        }
        return feeling
    }

    override fun find(id: FeelingId): Feeling? =
        database.from(FeelingTable)
            .select(FeelingTable.columns)
            .where(FeelingTable.externalId eq id)
            .map(::toFeeling)
            .firstOrNull()
}

private fun toFeeling(row: QueryRowSet) = Feeling(
    id = row[FeelingTable.externalId]!!
)
