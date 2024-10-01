package com.imagine.jordanpass.tools.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.imagine.jordanpass.tools.utils.SharedUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun sharedPreferencesProvider(
        @ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("visitPetra_preferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedManager(preferences: SharedPreferences): SharedUtils {
        return SharedUtils(preferences)
    }


}