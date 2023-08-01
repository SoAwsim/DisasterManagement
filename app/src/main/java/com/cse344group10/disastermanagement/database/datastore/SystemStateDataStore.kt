package com.cse344group10.disastermanagement.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SystemStateDataStore(private val context: Context) {
    companion object {
        private val Context.ds : DataStore<Preferences> by preferencesDataStore(name = "system_state")
        private val SYSTEM_STATE_KEY = booleanPreferencesKey("SYSTEM_STATE")
    }

    suspend fun storeSystemState(state: Boolean) {
        context.ds.edit {
            it[SYSTEM_STATE_KEY] = state
        }
    }

    val systemStateFlow: Flow<Boolean> = context.ds.data.map {
        it[SYSTEM_STATE_KEY] ?: false
    }
}