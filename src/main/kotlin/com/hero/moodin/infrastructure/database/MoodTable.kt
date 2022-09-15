package com.hero.moodin.infrastructure.database

import com.hero.feelin.domain.model.MoodId
import com.hero.feelin.domain.model.MoodType
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.timestamp

object MoodTable : Table<Nothing>("mood") {

    val id = int("id").primaryKey()
    val externalId = text("external_id").transform(fromUnderlyingValue = ::MoodId, toUnderlyingValue = MoodId::asString)
    val type = enum<MoodType>("type")
    val createdAt = timestamp("created_at")
    val user = int("user")
}
