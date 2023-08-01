package com.cse344group10.disastermanagement.database.map

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingAndEmergencyDepotInfo(
    @Embedded
    val buildingInfo: BuildingInfo,

    @Relation(
        parentColumn = "buildingEmergencyData",
        entityColumn = "emergencyDepotId"
    )
    val emergencyDepotInfo: EmergencyDepotInfo
)