package com.cse344group10.disastermanagement.database.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BuildingInfo(
    @PrimaryKey
    val buildingId : Int,

    val buildingYear : Int,
    val buildingResidentCount : Int,
    val buildingType : Int,
    val buildingDurability : Int,
    val buildingICoordinate : Int,
    val buildingJCoordinate : Int,
    val buildingEmergencyData : Int,
    val buildingMedicalData : Int,
    val buildingSarData : Int
)