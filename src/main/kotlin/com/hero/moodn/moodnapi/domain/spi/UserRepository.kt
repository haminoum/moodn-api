package com.hero.moodn.moodnapi.domain.spi

import com.hero.moodn.moodnapi.domain.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    /**
     * Creates a new user
     */
    fun create(user: User): User

    /**
     * Finds a user by username
     */
    fun find(username: String): User?

    /**
     * Creates or updates users
     */
    fun createOrUpdateAll(users: Set<User>)
}
