package com.ktl.tareasapp.di

import android.content.Context
import com.ktl.tareasapp.ui.theme.ThemePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Provides
    @Singleton
    fun provideThemePreferences(
        @ApplicationContext context : Context) : ThemePreferences =
        ThemePreferences(context)
}