package com.cse344group10.disastermanagement.database.map

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingAndAllInfo(
    @Embedded
    val buildingInfo: BuildingInfo,

    @Relation(
        parentColumn = "buildingEmergencyData",
        entityColumn = "emergencyDepotId"
    )
    val emergencyDepotInfo: EmergencyDepotInfo,

    @Relation(
        parentColumn = "buildingMedicalData",
        entityColumn = "medicalInfoId"
    )
    val medicalInfo: MedicalInfo,

    @Relation(
        parentColumn = "buildingSarData",
        entityColumn = "sarId"
    )
    val sarInfo: SarInfo
)