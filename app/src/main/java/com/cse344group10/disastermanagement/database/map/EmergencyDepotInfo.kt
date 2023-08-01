package com.cse344group10.disastermanagement.database.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmergencyDepotInfo(
    @PrimaryKey
    val emergencyDepotId: Int,

    val foodCount: Int,
    val shelterCount: Int,
    val transportVehicleCount: Int,
    val utilCount: Int,
    val babySupplyCount: Int,
    val liquidAmount: Int,
    val waterAmount: Int,
    val toiletCount: Int,
    val powerSupplyCount: Int
)