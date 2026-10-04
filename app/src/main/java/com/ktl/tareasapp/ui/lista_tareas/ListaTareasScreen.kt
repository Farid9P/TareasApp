package com.ktl.tareasapp.ui.lista_tareas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ktl.tareasapp.ui.viewmodel.TareaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaTareasScreen(
    viewModel : TareaViewModel = hiltViewModel(),
    navController : NavHostController
){

    val tareas by viewModel.tareas.collectAsState()

    var query by remember { mutableStateOf("") }
    val tareasFiltradas by viewModel.tareasFiltradas.collectAsState()
    val tareasAmostrar = if (query.isEmpty()) tareas else tareasFiltradas

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Mis tareas")
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("pantallaAjustes")
                    }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Ajustes"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //
                    navController.navigate("pantallaAgregarTarea")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ){  paddingValues ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            OutlinedTextField( //Campo de texto para que el usuario escriba lo que quiera
                value = query,
                onValueChange = {
                    query = it
                    viewModel.buscarTareas(query)
                },
                label = { Text(text = "Buscar por título o descripción") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Si no hay tareas en la lista, muestra el Box
            if (tareasAmostrar.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No hay tareas aún. Agrega una nueva")
                }
            }else{ //Si existe una tarea o más, mostraremos el LazyColumn
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(tareasAmostrar){ tarea ->
                        TareaItem(
                            tarea = tarea,
                            onEliminarClick = { tareaSeleccionada ->
                                viewModel.eliminarTarea(tareaSeleccionada, query)

                                coroutineScope.launch {
                                    val resultado = snackbarHostState.showSnackbar(
                                        message = "Tarea eliminada",
                                        actionLabel = "Deshacer",
                                        duration = SnackbarDuration.Short
                                    )

                                    if (resultado == SnackbarResult.ActionPerformed){
                                        viewModel.restaurarTarea(query)
                                    }

                                }
                                              },
                            onEditarClick = { tareaSeleccionada ->
                                navController.navigate("editarTareaScreen/${tareaSeleccionada.id}")},
                            onVerDetalleClick = {tareaSeleccionada->
                                navController.navigate("detalle/${tareaSeleccionada.id}")
                            })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

        }














    }

}
