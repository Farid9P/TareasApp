package com.ktl.tareasapp.ui.lista_tareas

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.ktl.tareasapp.data.local.entity.TareaEntity

@Composable
fun TareaItem(
    tarea : TareaEntity,
    onEliminarClick : (TareaEntity) -> Unit,
    onEditarClick : (TareaEntity) -> Unit,
    onVerDetalleClick : (TareaEntity) -> Unit){

    var mostrarDialogo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable { onVerDetalleClick(tarea) }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            //Información de una tarea
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                // Título de la tarea
                Text(
                    text = tarea.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis //Regar plantas de jardín por ...
                )

                Spacer(modifier = Modifier.height(4.dp))

                //Descripción
                Text(
                    text = tarea.descripcion ?: "Sin descripción",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                //Fecha
                Text(
                    text = "Creada: ${tarea.fechaCreacion}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Botones de acción (eliminar - actualizar)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Botón de eliminar
                IconButton(
                    onClick = {
                        mostrarDialogo = true
                    },
                    modifier = Modifier.minimumInteractiveComponentSize() //área de 48dp x 48dp
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar tarea",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red
                    )
                }

                // Botón de actualizar
                IconButton(
                    onClick = {
                        onEditarClick(tarea)
                    },
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar tarea",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Blue
                    )
                }

            }


        }


    }

    if (mostrarDialogo){
        AlertDialog(
            onDismissRequest = {
                mostrarDialogo = false },
            title = { Text(text = "¿Eliminar tarea?") },
            text = { Text(text = "¿Estás seguro(a) de eliminar esta tarea?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminarClick(tarea)
                        mostrarDialogo = false

                        
                    }
                ) {
                    Text(text = "Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                    }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}