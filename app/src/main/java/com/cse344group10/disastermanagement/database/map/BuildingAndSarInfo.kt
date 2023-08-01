package com.cse344group10.disastermanagement.database.map

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingAndSarInfo(
    @Embedded
    val buildingInfo: BuildingInfo,

    @Relation(
        parentColumn = "buildingSarData",
        entityColumn = "sarId"
    )
    val sarInfo: SarInfo
)