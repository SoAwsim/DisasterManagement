package com.cse344group10.disastermanagement.database.repository

import android.content.Context
import com.cse344group10.disastermanagement.database.Db
import com.cse344group10.disastermanagement.database.map.BuildingInfo
import com.cse344group10.disastermanagement.database.map.EmergencyDepotInfo
import com.cse344group10.disastermanagement.database.map.MedicalInfo
import com.cse344group10.disastermanagement.database.map.SarInfo
import com.cse344group10.disastermanagement.database.users.SupplyRequestsAndUser
import com.cse344group10.disastermanagement.database.users.SupplyRequestsTable
import com.cse344group10.disastermanagement.database.users.UserTable
import com.cse344group10.disastermanagement.models.map.Building
import com.cse344group10.disastermanagement.models.map.EmergencyDepot
import com.cse344group10.disastermanagement.models.map.MedicalSupplyDepot
import com.cse344group10.disastermanagement.models.map.SarCenter
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.models.user.User.BloodType.*
import com.cse344group10.disastermanagement.models.user.User.Gender.*
import com.cse344group10.disastermanagement.models.user.User.PermissionLevel
import com.cse344group10.disastermanagement.models.user.User.SecurityQuestion.*

class DbRepo(context: Context) {
    private val _db = Db.getDatabase(context)

    enum class BuildingTypes {
        Building,
        EmergencySupplyDepot,
        MedicalSupplyDepot,
        SarCenter
    }

    suspend fun insertSupplyRequest(supplyRequestsTable: SupplyRequestsTable) {
        _db.mapDao().insertSupplyRequest(supplyRequestsTable)
    }

    suspend fun getAllSupplyRequests(): List<SupplyRequestsAndUser> {
        return _db.mapDao().getAllSupplyRequests()
    }

    suspend fun getAllSupplyRequestsOfUser(id: Int): List<SupplyRequestsAndUser> {
        return _db.mapDao().getAllSupplyRequestsOfUser(id)
    }

    suspend fun updateBuilding(building: Building) {
        when (building) {
            is EmergencyDepot -> {
                _db.mapDao().updateEmergencyInfo(
                    EmergencyDepotInfo(
                        emergencyDepotId = building.emergencyInfoId,
                        foodCount = building.foodCount,
                        shelterCount = building.shelterCount,
                        transportVehicleCount = building.transportVehicleCount,
                        utilCount = building.utilCount,
                        babySupplyCount = building.babySupplyCount,
                        liquidAmount = building.liquidAmount,
                        waterAmount = building.waterAmount,
                        toiletCount = building.toiletCount,
                        powerSupplyCount = building.powerSupplyCount
                    )
                )
            }
            is MedicalSupplyDepot -> {
                _db.mapDao().updateMedicalInfo(
                    MedicalInfo(
                        medicalInfoId = building.medicalInfoId,
                        medicineHumanA = building.medicineHumanACount,
                        medicineHumanB = building.medicineHumanBCount,
                        medicineHumanC = building.medicineHumanCCount,
                        medicineAnimalA = building.medicineAnimalACount,
                        medicineAnimalB = building.medicineAnimalBCount,
                        medicineAnimalC = building.medicineAnimalCCount
                    )
                )
            }
            is SarCenter -> {
                _db.mapDao().updateSarInfo(
                    SarInfo(
                        sarId = building.sarInfoId,
                        sarHumanCount = building.sarHumanCount,
                        sarVehicleCount = building.sarVehicleCount
                    )
                )
            }
            else -> {
                _db.mapDao().updateBuilding(
                    BuildingInfo(
                        buildingId = building.buildingId,
                        buildingYear = building.buildingYear,
                        buildingResidentCount = building.residentCount,
                        buildingType = 0,
                        buildingDurability = building.buildingDurability,
                        buildingICoordinate = building.buildingI,
                        buildingJCoordinate = building.buildingJ,
                        buildingEmergencyData = 0,
                        buildingSarData = 0,
                        buildingMedicalData = 0
                    )
                )
            }
        }
    }

    suspend fun getBuildingCount(): Int {
        return _db.mapDao().getBuildingCount()
    }

    suspend fun getAllBuildingsAndInfo(): List<Building> {
        val dbBuildingList = _db.mapDao().getAllBuildings()
        val buildingList = ArrayList<Building>()

        dbBuildingList.forEach { currentBuilding ->
            when (currentBuilding.buildingInfo.buildingType) {
                0 -> buildingList.add(
                    Building(
                        buildingInfo = currentBuilding.buildingInfo
                    )
                )
                1 -> buildingList.add(
                    EmergencyDepot(
                        buildingInfo = currentBuilding.buildingInfo,
                        emergencyData = currentBuilding.emergencyDepotInfo
                    )
                )
                2 -> buildingList.add(
                    MedicalSupplyDepot(
                        buildingInfo = currentBuilding.buildingInfo,
                        medicalData = currentBuilding.medicalInfo
                    )
                )
                3 -> buildingList.add(
                    SarCenter(
                        buildingInfo = currentBuilding.buildingInfo,
                        sarInfo = currentBuilding.sarInfo
                    )
                )
            }
        }
        return buildingList
    }

    suspend fun insertUserTable(user: UserTable) {
        _db.mapDao().insertUserData(user)
    }

    suspend fun deleteUser(user: User) {
        _db.mapDao().deleteUserData(
            UserTable(
                userId = user.userId,
                userName = user.userName,
                userSurname = user.userSurname,
                userMail = user.userMail,
                userPassword = user.userPassword,
                userPermissionLevel = when (user.userPermissionLevel) {
                    PermissionLevel.User -> 0
                    PermissionLevel.EmergencySupplyManager -> 1
                    PermissionLevel.AnimalMedicalManager -> 2
                    PermissionLevel.HumanMedicalManager -> 3
                    PermissionLevel.HRManagerSAR -> 4
                    PermissionLevel.EquipmentManagerSAR -> 5
                    PermissionLevel.SystemAdministrator -> 6
                },
                userBloodType = when (user.userBloodType) {
                    Ap -> 0
                    An -> 1
                    Bp -> 2
                    Bn -> 3
                    ABp -> 4
                    ABn -> 5
                    Zp -> 6
                    Zn -> 7
                },
                userHouseId = user.userHouse.buildingId,
                userGender = when (user.userGender) {
                    Male -> 0
                    Female -> 1
                },
                userAge = user.userAge,
                userSecurityQuestionType = when (user.userSecurityQuestion) {
                    Pet -> 0
                    BookMovie -> 1
                    City -> 2
                    Mother -> 3
                    Vacation -> 4
                },
                userSecurityQuestionAnswer = user.userSecurityQuestionAnswer,
                userNotes = user.userNotes
            )
        )
    }

    suspend fun updateUser(user: User) {
        _db.mapDao().updateUser(
            UserTable(
                userId = user.userId,
                userName = user.userName,
                userSurname = user.userSurname,
                userMail = user.userMail,
                userPassword = user.userPassword,
                userPermissionLevel = when (user.userPermissionLevel) {
                    PermissionLevel.User -> 0
                    PermissionLevel.EmergencySupplyManager -> 1
                    PermissionLevel.AnimalMedicalManager -> 2
                    PermissionLevel.HumanMedicalManager -> 3
                    PermissionLevel.HRManagerSAR -> 4
                    PermissionLevel.EquipmentManagerSAR -> 5
                    PermissionLevel.SystemAdministrator -> 6
                },
                userBloodType = when (user.userBloodType) {
                    Ap -> 0
                    An -> 1
                    Bp -> 2
                    Bn -> 3
                    ABp -> 4
                    ABn -> 5
                    Zp -> 6
                    Zn -> 7
                },
                userHouseId = user.userHouse.buildingId,
                userGender = when (user.userGender) {
                    Male -> 0
                    Female -> 1
                },
                userAge = user.userAge,
                userSecurityQuestionType = when (user.userSecurityQuestion) {
                    Pet -> 0
                    BookMovie -> 1
                    City -> 2
                    Mother -> 3
                    Vacation -> 4
                },
                userSecurityQuestionAnswer = user.userSecurityQuestionAnswer,
                userNotes = user.userNotes
            )
        )
    }

    suspend fun getUserCount(): Int {
        return _db.mapDao().getUserCount()
    }

    suspend fun getUserByMail(mail: String): User? {
        val userTableList = _db.mapDao().findUserByMail(mail)
        return if (userTableList.isEmpty()) {
            null
        } else {
            User(userTableList[0])
        }
    }

    suspend fun getUserById(id: Int): User? {
        val userList = _db.mapDao().getUserById(id)
        return if (userList.isEmpty()) {
            null
        } else {
            User(userList[0])
        }
    }

    suspend fun searchForUser(mail: String, password: String): User? {
        val dbUserList = _db.mapDao().findMatchingUser(mail, password)
        return if (dbUserList.isEmpty()) {
            null
        } else {
            User(dbUserList[0])
        }
    }

    suspend fun getAllUsers(): List<User> {
        val dbUserList = _db.mapDao().getAllUsersAndBuildings()
        val userList = ArrayList<User>()

        dbUserList.forEach { currentUser ->
            userList.add(User(currentUser))
        }
        return userList
    }

    suspend fun createMap() {
        val sectorArray = Array(25) { BuildingTypes.Building }
        sectorArray[0] = BuildingTypes.EmergencySupplyDepot
        sectorArray[1] = BuildingTypes.MedicalSupplyDepot
        sectorArray[2] = BuildingTypes.SarCenter

        var arrayIndex: Int
        var currentBuildingId = 0
        var currentEmergencyId = 1
        var currentMedicalId = 1
        var currentSarId = 1
        // null lines for all info tables
        _db.mapDao().insertEmergencyData(
            EmergencyDepotInfo(
                emergencyDepotId = 0,
                foodCount = 0,
                shelterCount = 0,
                transportVehicleCount = 0,
                utilCount = 0,
                babySupplyCount = 0,
                liquidAmount = 0,
                waterAmount = 0,
                toiletCount = 0,
                powerSupplyCount = 0
            )
        )

        _db.mapDao().insertMedicalData(
            MedicalInfo(
                medicalInfoId = 0,
                medicineAnimalA = 0,
                medicineAnimalB = 0,
                medicineAnimalC = 0,
                medicineHumanA = 0,
                medicineHumanB = 0,
                medicineHumanC = 0
            )
        )

        _db.mapDao().insertSarData(
            SarInfo(
                sarId = 0,
                sarHumanCount = 0,
                sarVehicleCount = 0
            )
        )

        var iBias: Int
        var jBias: Int
        for (sector in 0..8) {
            sectorArray.shuffle()
            arrayIndex = 0
            iBias = (sector / 3) * 5
            jBias = (sector % 3) * 5
            for (i in 0..4) {
                for (j in 0..4) {
                    when (sectorArray[arrayIndex]) {
                        BuildingTypes.Building -> {
                            _db.mapDao().insertBuildings(
                                BuildingInfo(
                                    buildingId = currentBuildingId,
                                    buildingYear = (1995..2022).random(),
                                    buildingType = 0,
                                    buildingDurability = (0..4).random(),
                                    buildingResidentCount = (50..120).random(),
                                    buildingICoordinate = iBias + i,
                                    buildingJCoordinate = jBias + j,
                                    buildingEmergencyData = 0,
                                    buildingMedicalData = 0,
                                    buildingSarData = 0
                                )
                            )
                        }

                        BuildingTypes.EmergencySupplyDepot -> {
                            _db.mapDao().insertEmergencyData(
                                EmergencyDepotInfo(
                                    emergencyDepotId = currentEmergencyId,
                                    foodCount = (100000..300000).random(),
                                    shelterCount = (9000..15000).random(),
                                    transportVehicleCount = (20..80).random(),
                                    utilCount = (5000..10000).random(),
                                    babySupplyCount = (2000..3000).random(),
                                    liquidAmount = (500000..1000000).random(),
                                    waterAmount = (100000..500000).random(),
                                    toiletCount = (50..100).random(),
                                    powerSupplyCount = (500..1000).random()
                                )
                            )

                            _db.mapDao().insertBuildings(
                                BuildingInfo(
                                    buildingId = currentBuildingId,
                                    buildingYear = (2020..2022).random(),
                                    buildingType = 1,
                                    buildingDurability = 5,
                                    buildingResidentCount = 0,
                                    buildingICoordinate = iBias + i,
                                    buildingJCoordinate = jBias + j,
                                    buildingEmergencyData = currentEmergencyId,
                                    buildingMedicalData = 0,
                                    buildingSarData = 0
                                )
                            )
                            currentEmergencyId++
                        }

                        BuildingTypes.MedicalSupplyDepot -> {
                            _db.mapDao().insertMedicalData(
                                MedicalInfo(
                                    medicalInfoId = currentMedicalId,
                                    medicineAnimalA = (0..2000).random(),
                                    medicineAnimalB = (0..1000).random(),
                                    medicineAnimalC = (0..100).random(),
                                    medicineHumanA = (0..5000).random(),
                                    medicineHumanB = (0..2000).random(),
                                    medicineHumanC = (0..100).random()
                                )
                            )

                            _db.mapDao().insertBuildings(
                                BuildingInfo(
                                    buildingId = currentBuildingId,
                                    buildingYear = (2020..2022).random(),
                                    buildingType = 2,
                                    buildingDurability = 5,
                                    buildingResidentCount = 0,
                                    buildingICoordinate = iBias + i,
                                    buildingJCoordinate = jBias + j,
                                    buildingEmergencyData = 0,
                                    buildingMedicalData = currentMedicalId,
                                    buildingSarData = 0
                                )
                            )
                            currentMedicalId++
                        }

                        BuildingTypes.SarCenter -> {
                            _db.mapDao().insertSarData(
                                SarInfo(
                                    sarId = currentSarId,
                                    sarHumanCount = (200..500).random(),
                                    sarVehicleCount = (50..150).random()
                                )
                            )

                            _db.mapDao().insertBuildings(
                                BuildingInfo(
                                    buildingId = currentBuildingId,
                                    buildingYear = (2020..2022).random(),
                                    buildingType = 3,
                                    buildingDurability = 5,
                                    buildingResidentCount = 0,
                                    buildingICoordinate = iBias + i,
                                    buildingJCoordinate = jBias + j,
                                    buildingEmergencyData = 0,
                                    buildingMedicalData = 0,
                                    buildingSarData = currentSarId
                                )
                            )
                            currentSarId++
                        }
                    }
                    currentBuildingId++
                    arrayIndex++
                }
            }
        }
    }

    suspend fun createUsers() {
        val houseId = _db.mapDao().getARandomNormalBuilding()[0].buildingId
        _db.mapDao().insertUserData(
            UserTable(
                userId = 0,
                userName = "Yahya",
                userSurname = "Koyuncu",
                userMail = "yahya.koyuncu@example.com",
                userPassword = "1234",
                userPermissionLevel = 0, // normal user
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 0,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            ),
            UserTable(
                userId = 0,
                userName = "Taha Eren",
                userSurname = "Keleş",
                userMail = "tahaeren.keles@example.com",
                userPassword = "1234",
                userPermissionLevel = 1, // emergency supply depot manager
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 0,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            ),
            UserTable(
                userId = 0,
                userName = "Begüm",
                userSurname = "Yıldırım",
                userMail = "begum.yildirim@example.com",
                userPassword = "1234",
                userPermissionLevel = 2, // medical manager for animals
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 1,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            ),
            UserTable(
                userId = 0,
                userName = "İrem Tuğba",
                userSurname = "Sağsöz",
                userMail = "iremtugba.sagsoz@example.com",
                userPassword = "1234",
                userPermissionLevel = 3, // medical manager for humans
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 1,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            ),
            UserTable(
                userId = 0,
                userName = "Umut",
                userSurname = "Aydın",
                userMail = "umut.aydin@example.com",
                userPassword = "1234",
                userPermissionLevel = 4, // hr manager for sar
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 0,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            ),
            UserTable(
                userId = 0,
                userName = "Burak Eymen",
                userSurname = "Çevik",
                userMail = "burakeymen.cevik@example.com",
                userPassword = "1234",
                userPermissionLevel = 5, // vehicle manager for sar
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 0,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            ),
            UserTable(
                userId = 0,
                userName = "Oğuzhan",
                userSurname = "İçelliler",
                userMail = "oguzhan.icelliler@example.com",
                userPassword = "1234",
                userPermissionLevel = 6, // system administrator
                userBloodType = (0..7).random(),
                userHouseId = houseId,
                userGender = 0,
                userAge = (18..80).random(),
                userSecurityQuestionType = (0..4).random(),
                userSecurityQuestionAnswer = "Answer",
                userNotes = ""
            )
        )
    }
}