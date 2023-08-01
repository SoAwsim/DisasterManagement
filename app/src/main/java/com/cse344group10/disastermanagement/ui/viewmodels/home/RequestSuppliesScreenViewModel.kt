package com.cse344group10.disastermanagement.ui.viewmodels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.database.users.SupplyRequestsAndUser
import com.cse344group10.disastermanagement.database.users.SupplyRequestsTable
import com.cse344group10.disastermanagement.models.token.SessionToken
import kotlinx.coroutines.launch

class RequestSuppliesScreenViewModel: ViewModel() {
    val loaded = MutableLiveData(false)

    val showFoodAlertDialog = MutableLiveData(false)
    val foodAlertText = MutableLiveData("0")

    val showMedicalAlertDialog = MutableLiveData(false)
    val medicalAlertText = MutableLiveData("0")

    lateinit var requestList: List<SupplyRequestsAndUser>
        private set

    fun insertSupplyRequestMedical(repo: DbRepo) {
        viewModelScope.launch {
            repo.insertSupplyRequest(
                SupplyRequestsTable(
                    supplyId = 0,
                    supplyType = 1,
                    supplyAmount = medicalAlertText.value!!.toInt(),
                    supplyUser = SessionToken.currentlyLoggedUser!!.userId
                )
            )
            requestList = repo.getAllSupplyRequestsOfUser(SessionToken.currentlyLoggedUser!!.userId)
            showMedicalAlertDialog.value = false
        }
    }

    fun updateShowMedicalAlertDialog(state: Boolean) {
        showMedicalAlertDialog.value = state
    }

    fun updateMedicalAlertText(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            medicalAlertText.value = value
        }
    }

    fun insertSupplyRequestFood(repo: DbRepo) {
        viewModelScope.launch {
            repo.insertSupplyRequest(
                SupplyRequestsTable(
                    supplyId = 0,
                    supplyType = 0,
                    supplyAmount = foodAlertText.value!!.toInt(),
                    supplyUser = SessionToken.currentlyLoggedUser!!.userId
                )
            )
            requestList = repo.getAllSupplyRequestsOfUser(SessionToken.currentlyLoggedUser!!.userId)
            showFoodAlertDialog.value = false
        }
    }

    fun updateFoodAlertText(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            foodAlertText.value = value
        }
    }

    fun updateShowFoodAlertDialog(state: Boolean) {
        showFoodAlertDialog.value = state
    }

    fun getAllUserRequest(repo: DbRepo) {
        viewModelScope.launch {
            requestList = repo.getAllSupplyRequestsOfUser(SessionToken.currentlyLoggedUser!!.userId)
            loaded.value = true
        }
    }
}