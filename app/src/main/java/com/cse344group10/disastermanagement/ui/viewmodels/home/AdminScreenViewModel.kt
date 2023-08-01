package com.cse344group10.disastermanagement.ui.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.datastore.SystemStateDataStore
import kotlinx.coroutines.launch

class AdminScreenViewModel: ViewModel() {
    fun clearEmergency(systemStore: SystemStateDataStore) {
        viewModelScope.launch {
            systemStore.storeSystemState(false)
        }
    }
}