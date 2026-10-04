package com.ktl.tareasapp.data.repository

import com.ktl.tareasapp.data.local.dao.TareaDao
import com.ktl.tareasapp.data.local.entity.TareaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TareaRepository @Inject constructor(
    private val tareaDao : TareaDao
) {

    suspend fun insertarTarea (tarea : TareaEntity){
        tareaDao.insertarTarea(tarea)
    }

    suspend fun actualizarTarea (tarea: TareaEntity){
        tareaDao.actualizarTarea(tarea)
    }

    suspend fun obtenerTareaPorId(id : Int) : TareaEntity?{
        return tareaDao.obtenerTareaPorId(id)
    }

    suspend fun eliminarTarea (tarea : TareaEntity){
        tareaDao.eliminarTarea(tarea)
    }

    suspend fun eliminarTodasLasTareas (){
        tareaDao.eliminarTodasLasTareas()
    }

    fun obtenerTodasLasTareas() : Flow<List<TareaEntity>>{
        return tareaDao.obtenerTodasLasTareas()
    }

    suspend fun buscarTareas(query : String ) : List <TareaEntity>{
        return tareaDao.buscarTareas(query)
    }
}