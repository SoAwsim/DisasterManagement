package com.cse344group10.disastermanagement.ui.viewmodels.authentication

import androidx.compose.ui.geometry.Size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterPersonalViewModel: ViewModel() {
    val userName = MutableLiveData("")
    val userSurname = MutableLiveData("")
    val userAge = MutableLiveData("")
    val chosenIndex = MutableLiveData(0)
    val genderList = listOf("Male","Female")
    val ageError = MutableLiveData(false)
    val nameError = MutableLiveData(false)
    val surnameError = MutableLiveData(false)
    val bloodError = MutableLiveData(false)
    val expandBloodTypeDropDown = MutableLiveData(false)
    val bloodTypeList = arrayOf("A+","A-","B+","B-","AB+","AB-","0+","0-")
    val selectedBloodType = MutableLiveData("")
    val textFilledSize = MutableLiveData(Size.Zero)

    fun checkFields(): Boolean {
        if (userName.value!!.trim() == "") {
            nameError.value = true
        }
        if (userSurname.value!!.trim() == "") {
            surnameError.value = true
        }
        if (selectedBloodType.value!! == "") {
            bloodError.value = true
        }
        return (!nameError.value!! && !surnameError.value!! && !bloodError.value!!)
    }

    fun updateExpandBloodTypeDropDown(state: Boolean) {
        expandBloodTypeDropDown.value = state
    }

    fun updateTextFilledSize(size: Size) {
        textFilledSize.value = size
    }

    fun updateSelectedBloodType(type: String) {
        bloodError.value = false
        selectedBloodType.value = type
    }

    fun updateChosenIndex(index: Int) {
        chosenIndex.value = index
    }

    fun updateUserAge(age: String) {
        val regex = """[0-9]+""".toRegex()
        ageError.value = !age.matches(regex)
        userAge.value = age
    }

    fun updateUserSurname(surname: String) {
        surnameError.value = false
        userSurname.value = surname
    }

    fun updateUserName(name: String) {
        nameError.value = false
        userName.value = name
    }
}