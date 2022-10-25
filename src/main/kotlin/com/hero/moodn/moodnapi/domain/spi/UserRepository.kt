package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.User
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    /**
     * Creates a new user
     * @param user to be created
     * @return created User
     */
    fun create(user: User): User

    /**
     * Finds a user by username
     * @param username of requested User
     * @return found User
     */
    fun find(username: String): User?

    /**
     * Creates or updates users
     * @param users list to be updated
     */
    fun createOrUpdateAll(users: Set<User>)
}
