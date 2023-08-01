package com.cse344group10.disastermanagement.database.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionTokenDataStore(private val context: Context) {
    companion object {
        private val Context.ds : DataStore<Preferences> by preferencesDataStore(name = "session_token")
        private val SIGNED_USER_KEY = intPreferencesKey("SIGNED_USER")
    }

    suspend fun storeSessionToken(userId: Int) {
        context.ds.edit {
            it[SIGNED_USER_KEY] = userId
        }
    }

    val signedUserFlow: Flow<Int> = context.ds.data.map {
        it[SIGNED_USER_KEY] ?: 0
    }
}