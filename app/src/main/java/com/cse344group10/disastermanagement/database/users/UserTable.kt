package com.cse344group10.disastermanagement.database.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    val userId : Int, // auto generated id, when creating user just use 0 value

    val userName : String,
    val userSurname : String,
    val userMail : String,
    val userPassword : String,
    val userPermissionLevel : Int, // 0 = user, 1 = emergency supply manager,
    // 2 = medical animal manager, 3 = medical human manager, 4 = sar hr manager,
    // 5 = sar vehicle manager, 6 = system administrator
    val userBloodType : Int, // 0 = A+, 1 = A-, 2 = B+, 3 = B-, 4 = AB+, 5 = AB-, 6 = 0+, 7 = 0-
    val userHouseId : Int,
    val userGender : Int, // 0 = male, 1 = female
    val userAge : Int,
    val userSecurityQuestionType : Int, // 0 to 4 (5 questions)
    val userSecurityQuestionAnswer : String,
    val userNotes : String
)