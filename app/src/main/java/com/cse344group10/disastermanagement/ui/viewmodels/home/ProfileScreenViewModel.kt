package com.cse344group10.disastermanagement.ui.viewmodels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.token.SessionToken
import kotlinx.coroutines.launch

class ProfileScreenViewModel: ViewModel() {
    val showConfirmDialog = MutableLiveData(false)

    val showNameDialog = MutableLiveData(false)
    val nameDialogName = MutableLiveData(SessionToken.currentlyLoggedUser!!.userName)
    val nameDialogSurname = MutableLiveData(SessionToken.currentlyLoggedUser!!.userSurname)


    val showEmailDialog=MutableLiveData(false)
    val emailDialog =MutableLiveData(SessionToken.currentlyLoggedUser!!.userMail)
    val invalidEmail = MutableLiveData(false)

    val showPasswordDialog = MutableLiveData(false)
    val currentPassword = MutableLiveData("")
    val newPassword = MutableLiveData("")
    val confirmNewPassword = MutableLiveData("")

    val invalidCurrentPassword = MutableLiveData(false)
    val invalidNewPassword = MutableLiveData(false)
    val invalidConfirmPassword = MutableLiveData(false)

    val passwordVisibility = MutableLiveData(false)

    fun updateNameDialogSurname(surname: String) {
        nameDialogSurname.value = surname
    }

    fun updateNameDialogName(name: String) {
        nameDialogName.value = name
    }

    fun updateShowNameDialog(state: Boolean) {
        nameDialogName.value = SessionToken.currentlyLoggedUser!!.userName
        nameDialogSurname.value = SessionToken.currentlyLoggedUser!!.userSurname
        showNameDialog.value = state
    }

    fun updateShowConfirmDialog(state: Boolean) {
        showConfirmDialog.value = state
    }

    fun updateUserName(repo: DbRepo) {
        if (nameDialogName.value != "") {
            SessionToken.currentlyLoggedUser!!.userName = nameDialogName.value!!.trim()
        }
        if (nameDialogSurname.value != "") {
            SessionToken.currentlyLoggedUser!!.userSurname = nameDialogSurname.value!!.trim()
        }
        viewModelScope.launch {
            repo.updateUser(SessionToken.currentlyLoggedUser!!)
            showNameDialog.value = false
            showConfirmDialog.value = true
        }
    }

    fun updateShowEmailDialog(state: Boolean) {
        emailDialog.value = SessionToken.currentlyLoggedUser!!.userMail
        showEmailDialog.value = state
    }

    fun updateEmail(repo: DbRepo) {
        val check= """[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+""".toRegex()
        if (emailDialog.value!!.matches(check)) {
            SessionToken.currentlyLoggedUser!!.userMail = emailDialog.value!!.trim()
            viewModelScope.launch {
                repo.updateUser(SessionToken.currentlyLoggedUser!!)
                showEmailDialog.value = false
                showConfirmDialog.value = true
            }
        }
        else invalidEmail.value = true
    }

    fun checkUpdateEmail(mail: String) {
        invalidEmail.value = false
        emailDialog.value = mail
    }

    fun updateShowPasswordDialog(state: Boolean) {
        showPasswordDialog.value = state
    }

    fun updateUserPassword(repo: DbRepo){
        if (currentPassword.value == SessionToken.currentlyLoggedUser!!.userPassword){
            if(newPassword.value != "" && newPassword.value == confirmNewPassword.value){
                SessionToken.currentlyLoggedUser!!.userPassword = newPassword.value!!.trim()
                viewModelScope.launch {
                    repo.updateUser(SessionToken.currentlyLoggedUser!!)
                    showPasswordDialog.value = false
                    showConfirmDialog.value = true
                }
            }
            else {
                invalidConfirmPassword.value=true
                invalidNewPassword.value=true
            }
        }
        else{
            invalidCurrentPassword.value = true
        }
    }

    fun checkUpdateCurrentPassword(password: String) {
        invalidCurrentPassword.value = false
        currentPassword.value = password

    }

    fun checkUpdateConfirmPassword(cPassword: String) {
        invalidConfirmPassword.value = false
        confirmNewPassword.value = cPassword
    }

    fun checkUpdateNewPassword(nPassword: String) {
        invalidNewPassword.value = false
        newPassword.value = nPassword
    }

    fun changePasswordVisibility() {
        passwordVisibility.value = !passwordVisibility.value!!
    }
}























