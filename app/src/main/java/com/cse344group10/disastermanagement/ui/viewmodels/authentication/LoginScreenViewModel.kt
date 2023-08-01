package com.cse344group10.disastermanagement.ui.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.datastore.SessionTokenDataStore
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.token.SessionToken
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {
    val userMail = MutableLiveData("")
    val userPassword = MutableLiveData("")
    val passwordVisibility = MutableLiveData(false)
    val wrongLogin = MutableLiveData(false)

    fun updateUserMail(mail: String) {
        userMail.value = mail
        wrongLogin.value = false
    }

    fun updateUserPassword(password: String) {
        userPassword.value = password
        wrongLogin.value = false
    }

    fun changePasswordVisibility() {
        passwordVisibility.value = !passwordVisibility.value!!
    }

    fun signIn(repo: DbRepo, action: () -> Unit, tokenStore: SessionTokenDataStore) {
        viewModelScope.launch {
            val user = repo.searchForUser(mail = userMail.value!!, password = userPassword.value!!)
            if (user == null) {
                wrongLogin.value = true
            } else {
                tokenStore.storeSessionToken(user.userId)
                SessionToken.initToken(user)
                action()
            }
        }
    }
}