package com.cse344group10.disastermanagement.models.map

import com.cse344group10.disastermanagement.database.map.BuildingInfo
import com.cse344group10.disastermanagement.database.map.EmergencyDepotInfo

class EmergencyDepot(buildingInfo: BuildingInfo, emergencyData: EmergencyDepotInfo)
    : Building(buildingInfo) {

    enum class EmergencyDepotLimits(val value: Int) {
        FoodLimit(300000),
        ShelterLimit(15000),
        TransportVehicleLimit(80),
        UtilLimit(10000),
        BabySupplyLimit(3000),
        LiquidLimit(1000000),
        WaterLimit(500000),
        ToiletLimit(100),
        PowerSupplyLimit(1000)
    }

    val emergencyInfoId = emergencyData.emergencyDepotId

    var foodCount = emergencyData.foodCount
        set(value) {
            if (value < EmergencyDepotLimits.FoodLimit.value && value > 0) {
                field = value
            }
        }

    var shelterCount = emergencyData.shelterCount
        set(value) {
            if (value < EmergencyDepotLimits.ShelterLimit.value && value > 0) {
                field = value
            }
        }

    var transportVehicleCount = emergencyData.transportVehicleCount
        set(value) {
            if (value < EmergencyDepotLimits.TransportVehicleLimit.value && value > 0) {
                field = value
            }
        }

    var utilCount = emergencyData.utilCount
        set(value) {
            if (value < EmergencyDepotLimits.UtilLimit.value && value > 0) {
                field = value
            }
        }

    var babySupplyCount = emergencyData.babySupplyCount
        set(value) {
            if (value < EmergencyDepotLimits.BabySupplyLimit.value && value > 0) {
                field = value
            }
        }

    var liquidAmount = emergencyData.liquidAmount
        set(value) {
            if (value < EmergencyDepotLimits.LiquidLimit.value && value > 0) {
                field = value
            }
        }

    var waterAmount = emergencyData.waterAmount
        set(value) {
            if (value < EmergencyDepotLimits.WaterLimit.value && value > 0) {
                field = value
            }
        }

    var toiletCount = emergencyData.toiletCount
        set(value) {
            if (value < EmergencyDepotLimits.ToiletLimit.value && value > 0) {
                field = value
            }
        }

    var powerSupplyCount = emergencyData.powerSupplyCount
        set(value) {
            if (value < EmergencyDepotLimits.PowerSupplyLimit.value && value > 0) {
                field = value
            }
        }
}