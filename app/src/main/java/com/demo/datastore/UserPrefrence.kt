package com.demo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/* Step 1 : At the top level of your kotlin file:
 * property delegate created by preferencesDataStore
  *to create an instance of Datastore<Preferences>*/
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefrences")

class UserPrefrence(private val context: Context) {

    //1 : Define prefernce Key
    val COUNTER_KEY = intPreferencesKey("counters")

    //2 : Read value from datastore using Key
    val readCounter: Flow<Int> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[COUNTER_KEY] ?: 0
        }

    //4 : Write value to datastore using key
    suspend fun incrementCounter() {
        context.dataStore.edit { prefrences ->
            val currentCounterValue = prefrences[COUNTER_KEY] ?: 0
            prefrences[COUNTER_KEY] = currentCounterValue + 1
        }
    }

    //5. If you want to clear data store
    @Suppress("unused")
    suspend fun clearDataStore() {
        context.dataStore.edit {
            it.clear()
        }
    }

}