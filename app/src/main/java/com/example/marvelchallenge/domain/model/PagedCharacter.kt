package com.example.marvelchallenge.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    tableName = "page_character",
    primaryKeys = ["character_id"]
)
data class PagedCharacter(@ColumnInfo(name = "character_id") val id: Int, val page: Int)

data class CharacterPaged(@Embedded val character: Character, val page: Int)
