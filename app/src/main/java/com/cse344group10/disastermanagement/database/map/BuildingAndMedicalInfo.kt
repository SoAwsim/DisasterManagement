package com.cse344group10.disastermanagement.database.map

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingAndMedicalInfo(
    @Embedded
    val buildingInfo: BuildingInfo,

    @Relation(
        parentColumn = "buildingMedicalData",
        entityColumn = "medicalInfoId"
    )
    val medicalInfo: MedicalInfo
)