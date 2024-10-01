package com.imagine.jordanpass.tools.di

import android.content.Context
import androidx.room.Room
import com.imagine.jordanpass.tools.db.JDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): JDatabase = Room.databaseBuilder(context,
        JDatabase::class.java, "database-JGate").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideFavoriteDao(database: JDatabase) = database.favoriteDao()

    @Singleton
    @Provides
    fun provideTicketDao(database: JDatabase) = database.ticketDao()

}