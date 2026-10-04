package com.ktl.tareasapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ktl.tareasapp.data.local.entity.TareaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTarea(tarea : TareaEntity)

    @Query("SELECT * FROM tareas ORDER BY id DESC")
    fun obtenerTodasLasTareas() : Flow<List<TareaEntity>>

    @Update
    suspend fun actualizarTarea(tarea: TareaEntity)

    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun obtenerTareaPorId(id : Int) : TareaEntity?

    @Delete
    suspend fun eliminarTarea(tarea : TareaEntity)

    @Query("DELETE FROM tareas")
    suspend fun eliminarTodasLasTareas()

    //query = leer
    //titulo LIKE '%leer%' OR descripcion LIKE '%leer%'
    @Query("SELECT * FROM tareas WHERE titulo LIKE '%' || :query || '%' OR descripcion LIKE '%' || :query || '%' ")
    suspend fun buscarTareas(query : String) : List<TareaEntity>
}