package com.hero.moodn.moodnapi.infrastructure.database

import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.spi.UserRepository
import org.springframework.stereotype.Component

@Component
class DBUser : UserRepository {

    override fun create(user: User): User {
        createOrUpdateAll(setOf(user))
        return user
    }

    override fun find(username: String): User? {
        return TODO()
//        return database.from(UserTable)
//            .select(UserTable.columns)
//            .where(UserTable.username eq username)
//            .map(::toUser)
//            .firstOrNull()
    }

    override fun createOrUpdateAll(users: Set<User>) {
        return TODO()
//        database.bulkInsertOrUpdate(UserTable) {
//            users.forEach { user ->
//                item {
//                    set(UserTable.externalId, user.id)
//                    set(UserTable.username, user.username)
//                    set(UserTable.createdAt, user.createdAt)
//                }
//            }
//            onConflict(UserTable.externalId) {
//                set(UserTable.username, excluded(it.username))
//                set(UserTable.createdAt, excluded(it.createdAt))
//            }
//        }
    }

//    private fun toUser(row: QueryRowSet) = User(
//        id = row[UserTable.externalId]!!,
//        username = row[UserTable.username]!!,
//        createdAt = row[UserTable.createdAt]!!,
//    )
}
