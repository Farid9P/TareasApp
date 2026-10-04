package com.ktl.tareasapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ktl.tareasapp.data.local.dao.TareaDao
import com.ktl.tareasapp.data.local.entity.TareaEntity

@Database(
    entities = [TareaEntity::class],
    version = 1,
    exportSchema = false
)

abstract class TareaDatabase: RoomDatabase(){

    abstract fun tareaDao() : TareaDao

}