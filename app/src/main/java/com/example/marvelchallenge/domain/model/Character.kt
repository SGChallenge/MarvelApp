package com.example.marvelchallenge.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @Embedded val thumbnail: Thumbnail,
    @Embedded val comics: ItemContainer?
)

