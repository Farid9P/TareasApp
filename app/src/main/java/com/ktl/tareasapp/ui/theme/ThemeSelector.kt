package com.ktl.tareasapp.ui.theme

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ktl.tareasapp.ui.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelector(){

    val vm : ThemeViewModel = hiltViewModel()
    val mode by vm.themeMode.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf(ThemeMode.SYSTEM, ThemeMode.LIGHT, ThemeMode.DARK)

    ExposedDropdownMenuBox(expanded, onExpandedChange = {expanded = it}) {
        OutlinedTextField(
            readOnly = true,
            value = when(mode){
                ThemeMode.SYSTEM -> "Sistema"
                ThemeMode.LIGHT -> "Claro"
                ThemeMode.DARK -> "Oscuro"
            },
            onValueChange = {},
            label = { Text(text = "Tema") },

            modifier = Modifier.menuAnchor(
                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                enabled = true
            )
        )

        ExposedDropdownMenu(
            expanded,
            onDismissRequest = {expanded = false}
        ) {
            opciones.forEach{ m->
                DropdownMenuItem(
                    text = {
                        Text(
                            when(m){
                                ThemeMode.SYSTEM -> "Sistema"
                                ThemeMode.LIGHT -> "Claro"
                                ThemeMode.DARK -> "Oscuro"
                            }
                        )
                    },
                    onClick = {
                        vm.updateTheme(m)
                        expanded = false
                    }
                )
            }
        }
    }




}
