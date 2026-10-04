package com.ktl.tareasapp.ui.editar_tarea

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ktl.tareasapp.ui.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarTareaScreen(
    navController: NavController,
    tareaId : Int
){

    val viewModel : TareaViewModel = hiltViewModel()

    val context = LocalContext.current

    val tarea by viewModel.tareaSeleccionada.collectAsState(initial = null)

    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var datosCargados by remember { mutableStateOf(false) }
    var completada by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarTareaPorId(tareaId)
    }

    LaunchedEffect(tarea) {
        if (tarea != null && !datosCargados){
            titulo = tarea!!.titulo //Ir parque
            descripcion = tarea!!.descripcion ?: "" //Pasear a mascotas
            completada = tarea!!.completada
            datosCargados = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Editar tarea") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )

            )
        }
    ) {  paddingValues ->


        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = {titulo = it},
                label = { Text(text = "Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = {descripcion = it},
                label = { Text(text = "Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(text = "¿Tarea completada?",
                    modifier = Modifier.weight(1f))

                Switch(
                    checked = completada,
                    onCheckedChange = {isChecked ->
                        completada = isChecked
                        tarea?.let {
                            viewModel.actualizarTarea(
                                it.copy(completada = isChecked)
                            )
                        }
                    }
                )
            }

            Button(
                onClick = {
                    tarea?.let {
                        viewModel.actualizarTarea(
                            it.copy(
                                titulo = titulo,
                                descripcion = descripcion,
                                completada = completada)
                        )

                        Toast.makeText(
                            context,
                            "Tarea actualizada",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Actualizar tarea")
            }
        }











    }

}
