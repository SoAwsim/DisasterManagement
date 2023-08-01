package com.cse344group10.disastermanagement.ui.viewmodels.loading

import android.util.Log
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.map.Building

class MapLoadingScreenViewModel : ViewModel() {
    val showProgress = MutableLiveData(false)

    val expandDropDownList = MutableLiveData(false)
    val dropDownList = arrayOf("Sector 0", "Sector 1", "Sector 2", "Sector 3",
        "Sector 4", "Sector 5", "Sector 6", "Sector 7", "Sector 8")
    val selectedDropDownItem = MutableLiveData("Sector 0")
    val textFilledSize = MutableLiveData(Size.Zero)

    val showAlertDialog = MutableLiveData(false)
    lateinit var alertDialogBuilding : Building
        private set

    private lateinit var buildingAllElements : List<Building>
    lateinit var buildingUiElements : List<Building>
        private set

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
        repo.createMap()
        buildingAllElements = repo.getAllBuildingsAndInfo()
        val dbElementCount = buildingAllElements.count()
        Log.i("Database","Building database has been successfully generated " +
                "with $dbElementCount items")
        buildingUiElements = buildingAllElements.subList(0, 25)
        showProgress.value = true
    }
}