package com.cse344group10.disastermanagement.ui.viewmodels.authentication

import androidx.compose.ui.geometry.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.database.users.UserTable
import com.cse344group10.disastermanagement.models.map.Building
import kotlinx.coroutines.launch

class RegisterBuildingViewModel: ViewModel() {
    val showProgress = MutableLiveData(false)

    val expandDropDownList = MutableLiveData(false)
    val dropDownList = arrayOf("Sector 0", "Sector 1", "Sector 2", "Sector 3",
        "Sector 4", "Sector 5", "Sector 6", "Sector 7", "Sector 8")
    val selectedDropDownItem = MutableLiveData("Sector 0")
    val textFilledSize = MutableLiveData(Size.Zero)
    val selectedEmployeeRole = MutableLiveData("Emergency Supply Manager")
    val employeeRoleDropDown = MutableLiveData(false)

    val showChooseRoleDialog = MutableLiveData(false)
    val showAlertDialog = MutableLiveData(false)
    lateinit var alertDialogBuilding : Building

    private lateinit var buildingAllElements : List<Building>
    lateinit var buildingUiElements : List<Building>
        private set

    val isNextButtonEnabled = MutableLiveData(false)
    val selectedCardIndex = MutableLiveData(-1)
    val userCreatedDialog = MutableLiveData(false)

    fun updateEmployeeRoleDropDown(state: Boolean) {
        employeeRoleDropDown.value = state
    }

    fun updateSelectedEmployeeRole(role: String) {
        selectedEmployeeRole.value = role
    }

    fun updateShowChooseRoleDialog(state: Boolean) {
        showChooseRoleDialog.value = state
    }

    fun updateNextButtonEnabled(state: Boolean) {
        isNextButtonEnabled.value = state
    }

    fun updatedSelectedCardIndex(index: Int) {
        selectedCardIndex.value = index
    }

    fun dropDownMenuItemSelect(selectedSector: String) {
        when(selectedSector) {
            "Sector 0" -> buildingUiElements = buildingAllElements.subList(0, 25)
            "Sector 1" -> buildingUiElements = buildingAllElements.subList(25, 50)
            "Sector 2" -> buildingUiElements = buildingAllElements.subList(50, 75)
            "Sector 3" -> buildingUiElements = buildingAllElements.subList(75, 100)
            "Sector 4" -> buildingUiElements = buildingAllElements.subList(100, 125)
            "Sector 5" -> buildingUiElements = buildingAllElements.subList(125, 150)
            "Sector 6" -> buildingUiElements = buildingAllElements.subList(150, 175)
            "Sector 7" -> buildingUiElements = buildingAllElements.subList(175, 200)
            "Sector 8" -> buildingUiElements = buildingAllElements.subList(200, 225)
        }
        selectedDropDownItem.value = selectedSector
        expandDropDownList.value = false
    }

    fun updateTextFilledSize(size: Size) {
        textFilledSize.value = size
    }

    fun updateSelectedDropDownItem(string: String) {
        selectedDropDownItem.value = string
    }

    fun updateExpandDropList(boolean: Boolean) {
        expandDropDownList.value = boolean
    }

    fun alertDialogShow(building: Building) {
        alertDialogBuilding = building
        showAlertDialog.value = true
    }

    fun alertDialogHide() {
        showAlertDialog.value = false
    }

    suspend fun populateBuildingDataBase(repo: DbRepo) {
        buildingAllElements = repo.getAllBuildingsAndInfo()
        buildingUiElements = buildingAllElements.subList(0, 25)
        showProgress.value = true
    }

    fun addUser(
        mail: String,
        password: String,
        name: String,
        surname: String,
        age: Int,
        gender: Int,
        blood: Int,
        question: Int,
        answer: String,
        notes: String,
        permission: Int = 0,
        repo: DbRepo
    ) {
        viewModelScope.launch {
            repo.insertUserTable(
                UserTable(
                    userId = 0,
                    userName = name.trim(),
                    userSurname = surname.trim(),
                    userMail = mail.trim(),
                    userPassword = password,
                    userPermissionLevel = permission,
                    userBloodType = blood,
                    userHouseId = selectedCardIndex.value!!,
                    userGender = gender,
                    userAge = age,
                    userSecurityQuestionType = question,
                    userSecurityQuestionAnswer = answer,
                    userNotes = notes.trim()
                )
            )
            userCreatedDialog.value = true
        }
    }
}