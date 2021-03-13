package com.example.marvelchallenge.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.CharacterPaged
import com.example.marvelchallenge.domain.model.PagedCharacter

@Dao
abstract class MarvelHeroesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(list: List<Character>)

    @Query("SELECT * FROM Character WHERE id = :id")
    abstract fun loadById(id: Int): LiveData<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun savePaged(items: List<PagedCharacter>)

    @RewriteQueriesToDropUnusedColumns
    @Query(
        "SELECT * FROM page_character LEFT JOIN character ON id == character_id " +
            "ORDER BY name ASC"
    )
    abstract fun loadPaged(): PagingSource<Int, CharacterPaged>

    @Query("DELETE FROM page_character")
    abstract fun deletePaged()

    @Transaction
    open fun saveAll(characters: List<Character>, page: Int) {
        if (page == 0) {
            deletePaged()
        }
        savePaged(characters.map { PagedCharacter(it.id, page) })
        save(characters)
    }
}
