package com.cse344group10.disastermanagement.database.map

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MedicalInfo(
    @PrimaryKey
    val medicalInfoId:Int,

    val medicineHumanA:Int,
    val medicineHumanB:Int,
    val medicineHumanC:Int,
    val medicineAnimalA:Int,
    val medicineAnimalB:Int,
    val medicineAnimalC:Int
)