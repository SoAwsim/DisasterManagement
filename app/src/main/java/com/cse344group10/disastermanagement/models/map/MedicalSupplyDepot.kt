package com.cse344group10.disastermanagement.models.map

import com.cse344group10.disastermanagement.database.map.BuildingInfo
import com.cse344group10.disastermanagement.database.map.MedicalInfo

class MedicalSupplyDepot(buildingInfo: BuildingInfo, medicalData: MedicalInfo)
    : Building(buildingInfo) {

    enum class MedicalDepotLimits(val value: Int) {
        MedicineAnimalALimit(2000),
        MedicineAnimalBLimit(1000),
        MedicineAnimalCLimit(100),
        MedicineHumanALimit(5000),
        MedicineHumanBLimit(2000),
        MedicineHumanCLimit(100)
    }

    val medicalInfoId = medicalData.medicalInfoId

    var medicineAnimalACount = medicalData.medicineAnimalA
        set(value) {
            if (value < MedicalDepotLimits.MedicineAnimalALimit.value && value > 0) {
                field = value
            }
        }

    var medicineAnimalBCount = medicalData.medicineAnimalB
        set(value) {
            if (value < MedicalDepotLimits.MedicineAnimalBLimit.value && value > 0) {
                field = value
            }
        }

    var medicineAnimalCCount = medicalData.medicineAnimalC
        set(value) {
            if (value < MedicalDepotLimits.MedicineAnimalCLimit.value && value > 0) {
                field = value
            }
        }

    var medicineHumanACount = medicalData.medicineHumanA
        set(value) {
            if (value < MedicalDepotLimits.MedicineHumanALimit.value && value > 0) {
                field = value
            }
        }

    var medicineHumanBCount = medicalData.medicineHumanB
        set(value) {
            if (value < MedicalDepotLimits.MedicineHumanBLimit.value && value > 0) {
                field = value
            }
        }

    var medicineHumanCCount = medicalData.medicineHumanC
        set(value) {
            if (value < MedicalDepotLimits.MedicineHumanCLimit.value && value > 0) {
                field = value
            }
        }
}