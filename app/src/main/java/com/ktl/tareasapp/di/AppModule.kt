package com.ktl.tareasapp.di

import android.app.Application
import androidx.room.Room
import com.ktl.tareasapp.data.local.dao.TareaDao
import com.ktl.tareasapp.data.local.database.TareaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule{
    @Provides
    @Singleton
    fun provideTareaDatabase(context : Application) : TareaDatabase{
        return Room.databaseBuilder(
           context,
            TareaDatabase::class.java,
            "tarea_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTareaDao(db: TareaDatabase) : TareaDao{
        return db.tareaDao()
    }
}