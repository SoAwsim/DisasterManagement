package com.cse344group10.disastermanagement.database.users

import androidx.room.Embedded
import androidx.room.Relation
import com.cse344group10.disastermanagement.database.map.BuildingInfo

data class UserAndBuilding(
    @Embedded
    val userTable: UserTable,

    @Relation(
        parentColumn = "userHouseId",
        entityColumn = "buildingId"
    )
    val buildingInfo: BuildingInfo
)