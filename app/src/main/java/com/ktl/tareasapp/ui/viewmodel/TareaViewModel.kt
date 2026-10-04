package com.ktl.tareasapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ktl.tareasapp.data.local.entity.TareaEntity
import com.ktl.tareasapp.data.repository.TareaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val repository : TareaRepository
): ViewModel(){

    private val _tareas = MutableStateFlow<List<TareaEntity>>(emptyList())
    val tareas : StateFlow<List<TareaEntity>> = _tareas

    private val _tareaSeleccionada = MutableStateFlow<TareaEntity?>(null)
    val tareaSeleccionada : StateFlow<TareaEntity?> = _tareaSeleccionada


    private val _tareasFiltradas = MutableStateFlow<List<TareaEntity>>(emptyList())
    val tareasFiltradas : StateFlow<List<TareaEntity>> = _tareasFiltradas

    private var tareaEliminada : TareaEntity ?= null

    init {
        viewModelScope.launch {
            repository.obtenerTodasLasTareas().collect{
                _tareas.value = it
            }
        }
    }

    fun insertarTarea(tarea : TareaEntity){
        viewModelScope.launch {
            repository.insertarTarea(tarea)
        }
    }

    fun eliminarTarea (tarea : TareaEntity, texto : String){
        viewModelScope.launch {
            tareaEliminada = tarea
            repository.eliminarTarea(tarea)
            buscarTareas(texto)
        }
    }

    fun actualizarTarea(tarea : TareaEntity){
        viewModelScope.launch {
            repository.actualizarTarea(tarea)
        }
    }

    fun cargarTareaPorId(id : Int){
        viewModelScope.launch {
            val tarea = repository.obtenerTareaPorId(id)
            Log.d("ID", tarea?.id.toString())
            _tareaSeleccionada.value = tarea
        }
    }

    fun buscarTareas(query : String){
        viewModelScope.launch {
            val resultado = repository.buscarTareas(query)
            _tareasFiltradas.value = resultado
        }
    }


    fun restaurarTarea(texto : String){
        viewModelScope.launch {
            tareaEliminada?.let {
                repository.insertarTarea(it)
                tareaEliminada = null
                buscarTareas(texto)
            }
        }
    }














}