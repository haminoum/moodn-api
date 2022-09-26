package com.hero.moodn.moodnapi.infrastructure.database

<<<<<<< HEAD:src/main/kotlin/com/hero/moodn/moodnapi/infrastructure/database/UserTable.kt
import com.hero.moodn.moodnapi.domain.model.UserId
=======
/*
import com.hero.moodn.domain.model.UserId
>>>>>>> be0498e (feat:  setup db):src/main/kotlin/com/hero/moodn/infrastructure/database/UserTable.kt
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
*/
