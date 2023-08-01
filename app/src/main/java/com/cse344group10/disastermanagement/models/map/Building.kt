package com.cse344group10.disastermanagement.models.map

import com.cse344group10.disastermanagement.database.map.BuildingInfo

open class Building(buildingInfo: BuildingInfo) {
    val buildingDurability = buildingInfo.buildingDurability
    val buildingId = buildingInfo.buildingId

    // after implementing session token change getter
    var residentCount = buildingInfo.buildingResidentCount
        private set

    val buildingYear = buildingInfo.buildingYear
    val buildingI = buildingInfo.buildingICoordinate
    val buildingJ = buildingInfo.buildingJCoordinate

    fun collapse(earthquakeStrength: Int) {

    }
}