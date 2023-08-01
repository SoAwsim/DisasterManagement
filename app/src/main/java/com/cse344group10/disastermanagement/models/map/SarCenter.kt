package com.cse344group10.disastermanagement.models.map

import com.cse344group10.disastermanagement.database.map.BuildingInfo
import com.cse344group10.disastermanagement.database.map.SarInfo

class SarCenter(buildingInfo: BuildingInfo, sarInfo: SarInfo)
    : Building(buildingInfo) {

    enum class SarCenterLimits(val value: Int) {
        HumanCountLimit(500),
        VehicleCountLimit(150)
    }

    val sarInfoId = sarInfo.sarId

    var sarHumanCount = sarInfo.sarHumanCount
        set(value) {
            if (value < SarCenterLimits.HumanCountLimit.value && value > 0) {
                field = value
            }
        }

    var sarVehicleCount = sarInfo.sarVehicleCount
        set(value) {
            if (value < SarCenterLimits.VehicleCountLimit.value && value > 0) {
                field = value
            }
        }
}