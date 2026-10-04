package com.ktl.tareasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ktl.tareasapp.navigation.AppNavigation
import com.ktl.tareasapp.ui.theme.TareasAppTheme
import com.ktl.tareasapp.ui.theme.ThemeMode
import com.ktl.tareasapp.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot(){

    val vm : ThemeViewModel = hiltViewModel()
    val mode = vm.themeMode.collectAsState().value

    val isDark = when(mode){
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    TareasAppTheme(
        darkTheme = isDark,
        dynamicColor = true
    ) {

        val navController = rememberNavController()
        AppNavigation(navController = navController)
    }
}

