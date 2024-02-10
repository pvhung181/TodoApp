package vn.tutorial.todolist.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException


val Context.dataStore: DataStore<Preferences> by preferencesDataStore (name = "layout_preferences")

class DataStoreManager(
    val context: Context
) {

    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        const val TAG = "UserPreferencesRepo"
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    suspend fun saveDarkThemePreferences(isDarkTheme: Boolean) {
        context.dataStore.edit {
                preferences ->
            preferences[IS_DARK_THEME] = isDarkTheme
        }
    }

    fun getValueDarkTheme(): Boolean {
        var res = false
        runBlocking {
            with(Dispatchers.IO) {
               res = isDarkTheme.first()
            }
        }
        return res;
    }
}