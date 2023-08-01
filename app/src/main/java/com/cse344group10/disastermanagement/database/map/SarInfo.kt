package com.cse344group10.disastermanagement.database.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SarInfo(
    @PrimaryKey
    val sarId:Int,

    val sarHumanCount:Int,
    val sarVehicleCount:Int
)