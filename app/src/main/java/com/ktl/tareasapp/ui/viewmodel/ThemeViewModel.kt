package com.ktl.tareasapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktl.tareasapp.ui.theme.ThemeMode
import com.ktl.tareasapp.ui.theme.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel
@Inject constructor(private val prefs : ThemePreferences) : ViewModel(){

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode : StateFlow<ThemeMode> = _themeMode

    init {
        viewModelScope.launch {
            prefs.themeModeFlow.collect{ modo ->
                _themeMode.value = modo
            }
        }
    }

    fun updateTheme(mode : ThemeMode){
        viewModelScope.launch {
            prefs.setTheme(mode)
        }
    }
}