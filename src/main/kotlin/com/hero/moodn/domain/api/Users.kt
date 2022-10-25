package com.hero.moodn.domain.api

import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId

interface Users {
    /**
     * Creates a new user
     * @param user to be created
     */
    fun create(user: User)

    /**
     * Finds a user by username
     * @param userId of requested User
     * @return found [User]
     */
    fun find(userId: UserId): User?
}
