package com.example.marvelchallenge.data.di

import android.content.Context
import androidx.room.Room
import com.example.marvelchallenge.data.dao.EventDao
import com.example.marvelchallenge.data.dao.MarvelHeroesDao
import com.example.marvelchallenge.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MarvelDb"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMarvelHeroesDao(appDatabase: AppDatabase): MarvelHeroesDao {
        return appDatabase.marvelHeroesDao()
    }

    @Provides
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }
}
