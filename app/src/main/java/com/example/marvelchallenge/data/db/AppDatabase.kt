package com.example.marvelchallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvelchallenge.data.dao.EventDao
import com.example.marvelchallenge.data.dao.MarvelHeroesDao
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.Event
import com.example.marvelchallenge.domain.model.PagedCharacter

@Database(
    entities = [Character::class, Event::class, PagedCharacter::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marvelHeroesDao(): MarvelHeroesDao
    abstract fun eventDao(): EventDao
}
