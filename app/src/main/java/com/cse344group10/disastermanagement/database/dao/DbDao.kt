package com.cse344group10.disastermanagement.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cse344group10.disastermanagement.database.map.BuildingAndAllInfo
import com.cse344group10.disastermanagement.database.map.BuildingInfo
import com.cse344group10.disastermanagement.database.map.EmergencyDepotInfo
import com.cse344group10.disastermanagement.database.map.MedicalInfo
import com.cse344group10.disastermanagement.database.map.SarInfo
import com.cse344group10.disastermanagement.database.users.SupplyRequestsAndUser
import com.cse344group10.disastermanagement.database.users.SupplyRequestsTable
import com.cse344group10.disastermanagement.database.users.UserAndBuilding
import com.cse344group10.disastermanagement.database.users.UserTable

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildings(vararg buildingInfo: BuildingInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyData(vararg emergencyDepotInfo: EmergencyDepotInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicalData(vararg medicalInfo: MedicalInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSarData(vararg sarInfo: SarInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(vararg userTable: UserTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplyRequest(vararg supplyRequestsTable: SupplyRequestsTable)

    @Update
    suspend fun updateUser(vararg userTable: UserTable)

    @Update
    suspend fun updateBuilding(vararg buildingInfo: BuildingInfo)

    @Update
    suspend fun updateEmergencyInfo(vararg emergencyDepotInfo: EmergencyDepotInfo)

    @Update
    suspend fun updateMedicalInfo(vararg medicalInfo: MedicalInfo)

    @Update
    suspend fun updateSarInfo(vararg sarInfo: SarInfo)

    @Delete
    suspend fun deleteBuilding(buildingInfo: BuildingInfo)

    @Delete
    suspend fun deleteEmergencyData(emergencyDepotInfo: EmergencyDepotInfo)

    @Delete
    suspend fun deleteMedicalData(medicalInfo: MedicalInfo)

    @Delete
    suspend fun deleteSarData(sarInfo: SarInfo)

    @Delete
    suspend fun deleteUserData(userTable: UserTable)

    @Query("SELECT COUNT(*) FROM usertable")
    suspend fun getUserCount(): Int

    @Transaction
    @Query("SELECT * FROM supplyrequeststable ORDER by supplyId")
    suspend fun getAllSupplyRequests(): List<SupplyRequestsAndUser>

    @Transaction
    @Query("SELECT * FROM supplyrequeststable WHERE supplyUser = :userId ORDER by supplyId")
    suspend fun getAllSupplyRequestsOfUser(userId: Int): List<SupplyRequestsAndUser>

    @Transaction
    @Query("SELECT * FROM usertable ORDER by userId")
    suspend fun getAllUsersAndBuildings(): List<UserAndBuilding>

    @Transaction
    @Query("SELECT * FROM usertable WHERE userId = :id")
    suspend fun getUserById(id: Int): List<UserAndBuilding>

    @Transaction
    @Query("SELECT * FROM usertable WHERE userMail = :mail")
    suspend fun findUserByMail(mail: String): List<UserAndBuilding>

    @Transaction
    @Query("SELECT * FROM usertable " +
            "WHERE userMail = :mail AND userPassword = :password")
    suspend fun findMatchingUser(mail: String, password: String): List<UserAndBuilding>

    @Query("SELECT COUNT(*) FROM buildinginfo")
    suspend fun getBuildingCount(): Int

    @Query("SELECT * FROM buildinginfo " +
            "WHERE buildingEmergencyData = 0 AND buildingMedicalData = 0 AND buildingSarData = 0 " +
            "ORDER by RANDOM() LIMIT 1")
    suspend fun getARandomNormalBuilding(): List<BuildingInfo>

    @Transaction
    @Query("SELECT * FROM buildinginfo ORDER by buildingId")
    suspend fun getAllBuildings(): List<BuildingAndAllInfo>
}