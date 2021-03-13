package com.example.marvelchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.marvelchallenge.domain.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun save(list: List<Event>)

    @Query("DELETE FROM Event")
    abstract fun deleteAllEvents()

    @Query("SELECT * FROM Event ORDER BY start ASC")
    abstract fun load(): Flow<List<Event>>

    @Transaction
    open suspend fun saveAllEvents(list: List<Event>) {
        deleteAllEvents()
        save(list)
    }
}
