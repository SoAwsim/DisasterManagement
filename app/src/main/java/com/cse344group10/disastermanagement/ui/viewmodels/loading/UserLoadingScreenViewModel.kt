package com.cse344group10.disastermanagement.ui.viewmodels.loading

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.user.User

class UserLoadingScreenViewModel : ViewModel() {
    val showUsers = MutableLiveData(false)

    lateinit var uiUsers : List<User>
        private set

    suspend fun populateUserDatabase(repo: DbRepo) {
        repo.createUsers()
        uiUsers = repo.getAllUsers()
        val userDbElementCount = uiUsers.count()
        Log.i("Database:","User database has been successfully generated " +
                "with $userDbElementCount items")
        showUsers.value = true
    }
}