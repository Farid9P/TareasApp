package com.ktl.tareasapp.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

//0 -> SYSTEM
//1 -> light
//2 -> DARK
private val KEY_THEME_MODE = intPreferencesKey("theme_mode")

class ThemePreferences (private val context: Context){

    suspend fun setTheme(mode: ThemeMode){
        context.dataStore.edit { prefs ->
            prefs[KEY_THEME_MODE] = when(mode){
                ThemeMode.SYSTEM -> 0
                ThemeMode.LIGHT -> 1
                ThemeMode.DARK -> 2
            }
        }

        //setTheme(Theme.DARK)
        //theme_mode = 2
    }

    val themeModeFlow = context.dataStore.data.map { prefs->

        when(prefs[KEY_THEME_MODE] ?: 0){
            1 -> ThemeMode.LIGHT
            2 -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }

        //theme_mode = 1
        //Flow Theme.LIGHT

    }

}