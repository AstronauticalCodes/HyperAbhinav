package com.example.hyperabhinav.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object Persistent {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")
    suspend fun save(key: String, value: String, context: Context) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun load(key: String, context: Context): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun delete(key: String, context: Context) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings.remove(dataStoreKey)
        }
    }
}
