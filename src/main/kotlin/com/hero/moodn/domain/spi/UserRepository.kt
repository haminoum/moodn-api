package com.hero.moodn.domain.spi

import com.hero.moodn.domain.model.User
import com.hero.moodn.domain.model.UserId
import org.springframework.stereotype.Repository

@Repository
interface UserRepository {

    fun create(user: User): User

    fun find(userId: UserId): User?

    fun createOrUpdateAll(users: Set<User>)
}
