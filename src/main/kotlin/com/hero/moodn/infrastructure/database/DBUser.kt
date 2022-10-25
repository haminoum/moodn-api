package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import com.hero.moodn.domain.spi.UserRepository
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.postgresql.bulkInsertOrUpdate
import org.springframework.stereotype.Component

@Component
class DBUser(val database: Database) : UserRepository {

    override fun create(user: User): User {
        createOrUpdateAll(setOf(user))
        return user
    }

    override fun find(userId: UserId): User? {
        return database.from(UserTable)
            .select(UserTable.columns)
            .where(UserTable.externalId eq userId)
            .map(::toUser)
            .firstOrNull()
    }

    override fun createOrUpdateAll(users: Set<User>) {
        database.bulkInsertOrUpdate(UserTable) {
            users.forEach { user ->
                item {
                    set(UserTable.externalId, user.id)
                    set(UserTable.username, user.username)
                    set(UserTable.createdAt, user.createdAt)
                }
            }
            onConflict(UserTable.externalId) {
                set(UserTable.username, excluded(UserTable.username))
                set(UserTable.createdAt, excluded(UserTable.createdAt))
            }
        }
    }

    private fun toUser(row: QueryRowSet) = User(
        id = row[UserTable.externalId]!!,
        username = row[UserTable.username]!!,
        createdAt = row[UserTable.createdAt]!!,
    )
}
