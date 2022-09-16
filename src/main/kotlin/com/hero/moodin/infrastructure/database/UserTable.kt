package com.hero.moodin.infrastructure.database

import com.hero.moodin.domain.model.UserId
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.timestamp


object UserTable : Table<Nothing>("moodn_user") {

    val id = int("id").primaryKey()
    val externalId = text("external_id")
        .transform(fromUnderlyingValue = ::UserId, toUnderlyingValue = UserId::asString)
    val username = text("username")
    val createdAt = timestamp("created_at")
}
