package com.cse344group10.disastermanagement.database.users

import androidx.room.Embedded
import androidx.room.Relation

data class SupplyRequestsAndUser(
    @Embedded
    val requestsTable: SupplyRequestsTable,

    @Relation(
        parentColumn = "supplyUser",
        entityColumn = "userId"
    )
    val userTable: UserTable,
)
