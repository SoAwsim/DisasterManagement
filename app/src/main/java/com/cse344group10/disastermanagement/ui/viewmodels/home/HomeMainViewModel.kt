package com.cse344group10.disastermanagement.ui.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.datastore.SessionTokenDataStore
import com.cse344group10.disastermanagement.database.datastore.SystemStateDataStore
import com.cse344group10.disastermanagement.models.token.SessionToken
import kotlinx.coroutines.launch

class HomeMainViewModel: ViewModel() {
    fun signOut(tokenStore: SessionTokenDataStore) {
        viewModelScope.launch {
            tokenStore.storeSessionToken(0)
        }
    }

    fun changeSystemState(systemStore: SystemStateDataStore) {
        viewModelScope.launch {
            systemStore.storeSystemState(true)
        }
    }
}