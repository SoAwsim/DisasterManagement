package com.cse344group10.disastermanagement.ui.viewmodels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.user.User
import kotlinx.coroutines.launch

class EditUsersViewModel: ViewModel() {
    val showUsers = MutableLiveData(false)

    lateinit var uiUsers: ArrayList<User>
        private set

    val selectedCardIndex = MutableLiveData(-1)
    val isDeleteButtonPressed = MutableLiveData(false)
    val isChangePermissionButtonPressed = MutableLiveData(false)

    val updateExpandDropDownList = MutableLiveData(false)
    val selectedDropDownItem = MutableLiveData("")
    val showUserUpdate = MutableLiveData(false)
    val showUserDelete = MutableLiveData(false)

    fun updateShowUserDelete(state: Boolean) {
        showUserDelete.value = state
    }

    fun updateShowUserUpdate(state: Boolean) {
        showUserUpdate.value = state
    }

    fun updateSelectedCardIndex(value: Int) {
        selectedCardIndex.value = value
    }

    fun updateSelectedDropDownItem(item: String) {
        selectedDropDownItem.value = item
    }

    fun changeExpandDropDownList(state: Boolean) {
        updateExpandDropDownList.value = state
    }

    fun updateIsChangePermissionButtonPressed(state: Boolean) {
        isChangePermissionButtonPressed.value = state
    }

    fun updateIsDeleteButtonPressed(state: Boolean) {
        isDeleteButtonPressed.value = state
    }

    fun getUsers(repo: DbRepo) {
        viewModelScope.launch {
            uiUsers = repo.getAllUsers() as ArrayList<User>
            showUsers.value = true
        }
    }

    fun updateUser(repo: DbRepo) {
        viewModelScope.launch {
            val selectedUser = uiUsers[selectedCardIndex.value!!]
            selectedUser.userPermissionLevel = when (selectedDropDownItem.value) {
                "Emergency Supply Manager" -> User.PermissionLevel.EmergencySupplyManager
                "Animal Medical Manager" -> User.PermissionLevel.AnimalMedicalManager
                "Human Medical Manager" -> User.PermissionLevel.HumanMedicalManager
                "SAR HR Manager" -> User.PermissionLevel.HRManagerSAR
                "SAR Equipment Manager" -> User.PermissionLevel.EquipmentManagerSAR
                "System Administrator" -> User.PermissionLevel.SystemAdministrator
                else -> User.PermissionLevel.User
            }
            repo.updateUser(selectedUser)
            updateShowUserUpdate(true)
        }
    }

    fun deleteUser(repo: DbRepo) {
        viewModelScope.launch {
            val selectedUser = uiUsers[selectedCardIndex.value!!]
            repo.deleteUser(selectedUser)
            uiUsers.remove(selectedUser)
            updateShowUserDelete(true)
        }
    }
}