package com.cse344group10.disastermanagement.models.user

import com.cse344group10.disastermanagement.database.users.UserAndBuilding
import com.cse344group10.disastermanagement.models.map.Building

class User(userAndBuilding: UserAndBuilding) {
    enum class PermissionLevel {
        User,
        EmergencySupplyManager,
        AnimalMedicalManager,
        HumanMedicalManager,
        HRManagerSAR,
        EquipmentManagerSAR,
        SystemAdministrator
    }

    enum class BloodType {
        Ap,
        An,
        Bp,
        Bn,
        ABp,
        ABn,
        Zp,
        Zn
    }

    enum class Gender {
        Male,
        Female
    }

    enum class SecurityQuestion(val question: String) {
        Pet("What is the name of your first pet?"),
        BookMovie("What is your favorite book or movie?"),
        City("In which city were you born?"),
        Mother("What is the maiden name of your mother?"),
        Vacation("What is your favorite vacation destination?")
    }

    val userId = userAndBuilding.userTable.userId

    var userName = userAndBuilding.userTable.userName
    var userSurname = userAndBuilding.userTable.userSurname
    var userMail = userAndBuilding.userTable.userMail
    var userPassword = userAndBuilding.userTable.userPassword
    var userPermissionLevel = when (userAndBuilding.userTable.userPermissionLevel) {
        1 -> PermissionLevel.EmergencySupplyManager
        2 -> PermissionLevel.AnimalMedicalManager
        3 -> PermissionLevel.HumanMedicalManager
        4 -> PermissionLevel.HRManagerSAR
        5 -> PermissionLevel.EquipmentManagerSAR
        6 -> PermissionLevel.SystemAdministrator
        else -> PermissionLevel.User
    }
    val userBloodType = when (userAndBuilding.userTable.userBloodType) {
        1 -> BloodType.An
        2 -> BloodType.Bp
        3 -> BloodType.Bn
        4 -> BloodType.ABp
        5 -> BloodType.ABn
        6 -> BloodType.Zp
        7 -> BloodType.Zn
        else -> BloodType.Ap
    }
    val userHouse = Building(userAndBuilding.buildingInfo)
    val userGender = when (userAndBuilding.userTable.userGender) {
        0 -> Gender.Male
        else -> Gender.Female
    }
    var userAge = userAndBuilding.userTable.userAge
    var userSecurityQuestion = when (userAndBuilding.userTable.userSecurityQuestionType) {
        0 -> SecurityQuestion.Pet
        1 -> SecurityQuestion.BookMovie
        2 -> SecurityQuestion.City
        3 -> SecurityQuestion.Mother
        else -> SecurityQuestion.Vacation
    }
    var userSecurityQuestionAnswer = userAndBuilding.userTable.userSecurityQuestionAnswer
    var userNotes = userAndBuilding.userTable.userNotes
}