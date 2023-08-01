package com.cse344group10.disastermanagement.database.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SupplyRequestsTable(
    @PrimaryKey(autoGenerate = true)
    val supplyId: Int,

    val supplyType: Int,
    val supplyAmount: Int,
    val supplyUser: Int //fk
)
