package com.hero.moodn.infrastructure.database

import com.hero.moodn.domain.model.CommentId
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.timestamp

object CommentTable : Table<Nothing>("comment") {

    val id = int("id").primaryKey()
    val externalId =
        text("external_id").transform(fromUnderlyingValue = ::CommentId, toUnderlyingValue = CommentId::asString)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
    val author = int("author")
    val content = text("content")
}
