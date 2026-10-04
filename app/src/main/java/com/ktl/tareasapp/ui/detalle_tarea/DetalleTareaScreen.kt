package com.ktl.tareasapp.ui.detalle_tarea

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.ktl.tareasapp.R
import com.ktl.tareasapp.ui.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleTareaScreen(
    tareaId : Int,
    navController : NavController
){

    val viewModel : TareaViewModel = hiltViewModel()

    val tarea by viewModel.tareaSeleccionada.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        viewModel.cargarTareaPorId(tareaId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detalle de la Tarea")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
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
    ) { paddingValues ->

        tarea?.let {
           Column(
               modifier = Modifier
                   .padding(paddingValues)
                   .padding(16.dp)
                   .fillMaxSize(),
               verticalArrangement = Arrangement.Top,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {

               Image( //Icono
                   painter = painterResource(id = R.drawable.nota),
                   contentDescription = "Icono de una nota",
                   modifier = Modifier
                       .size(100.dp)
               )

               Spacer(modifier = Modifier.height(24.dp))

               Card(
                   modifier = Modifier.fillMaxWidth(),
                   elevation = CardDefaults.cardElevation(6.dp),
                   colors = CardDefaults.cardColors(
                       containerColor = MaterialTheme.colorScheme.surfaceVariant
                   )
               ) {
                   Column (modifier = Modifier.padding(20.dp)){

                       //Título de la tarea
                       Row (verticalAlignment = Alignment.CenterVertically){

                           Icon(Icons.Default.Star, contentDescription = "Título", tint = MaterialTheme.colorScheme.primary)
                           Spacer(modifier = Modifier.width(8.dp))
                           Text(text = "Título:", fontWeight = FontWeight.Bold)

                       }

                       Spacer(modifier = Modifier.height(5.dp))
                       Text(it.titulo, style = MaterialTheme.typography.bodyLarge)

                       Spacer(modifier = Modifier.height(16.dp))

                       //Descripción de la tarea
                       Row(
                           verticalAlignment = Alignment.CenterVertically) {

                           Icon(Icons.Default.Star, contentDescription = "Descripción", tint = MaterialTheme.colorScheme.primary)
                           Spacer(modifier = Modifier.width(8.dp))
                           Text(text = "Descripción", fontWeight = FontWeight.Bold)
                       }

                       Spacer(modifier = Modifier.height(5.dp))
                       Text(text = it.descripcion ?: "Sin descripción", style = MaterialTheme.typography.bodyLarge)

                       Spacer(modifier = Modifier.height(16.dp))

                       //Fecha de registro
                       Row(verticalAlignment = Alignment.CenterVertically) {
                           Icon(Icons.Default.DateRange, contentDescription = "Fecha de registro", tint = MaterialTheme.colorScheme.primary)
                           Spacer(modifier = Modifier.width(8.dp))
                           Text(text = "Fecha de registro:", fontWeight = FontWeight.Bold)
                       }

                       Spacer(modifier = Modifier.height(5.dp))
                       Text(text = it.fechaCreacion, style = MaterialTheme.typography.bodyLarge)

                       Spacer(modifier = Modifier.height(16.dp))

                       //Estado de la tarea
                       Row(verticalAlignment = Alignment.CenterVertically) {
                           Icon(Icons.Default.CheckCircle, contentDescription = "Estado", tint = MaterialTheme.colorScheme.primary)
                           Spacer(modifier = Modifier.width(8.dp))
                           Text(text = "Estado", fontWeight = FontWeight.Bold)
                       }

                       Spacer(modifier = Modifier.height(5.dp))

                       Text(text = if (it.completada) "✅ Completada" else "⏳ Pendiente",
                           color = if (it.completada) Color(0xFF388E3C) else Color(0xFFF57C00),
                           fontWeight = FontWeight.Bold
                       )

                   }
               }

           }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }










    }

}
