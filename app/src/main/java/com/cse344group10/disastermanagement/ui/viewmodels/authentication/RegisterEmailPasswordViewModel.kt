package com.cse344group10.disastermanagement.ui.viewmodels.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterEmailPasswordViewModel: ViewModel() {
    val userMail = MutableLiveData("")
    val userPassword = MutableLiveData("")
    val userConfirmPassword = MutableLiveData("")
    val invalidEmail = MutableLiveData(false)
    val invalidPassword = MutableLiveData(false)

    fun updateMail(mail: String) {
        invalidEmail.value = false
        userMail.value = mail
    }

    fun updatePassword(password: String) {
        invalidPassword.value = false
        userPassword.value = password
    }

    fun updateConfirmPassword(password: String) {
        invalidPassword.value = false
        userConfirmPassword.value = password
    }

    fun checkInfo() {
        val mailRegex = """[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+""".toRegex()
        if (!userMail.value!!.matches(mailRegex)) {
            invalidEmail.value = true
        }
        if (userPassword.value == "" ||
            userConfirmPassword.value == "" ||
            userPassword.value != userConfirmPassword.value
        ) {
            invalidPassword.value = true
        }
    }
}