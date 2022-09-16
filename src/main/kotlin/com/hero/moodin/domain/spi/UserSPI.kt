package com.hero.moodin.domain.spi

import com.hero.moodin.domain.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserSPI {

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
