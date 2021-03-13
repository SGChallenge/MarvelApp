package com.example.marvelchallenge.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey val id: String,
    val title: String,
    val start: String?,
    val end: String?,
    @Embedded val thumbnail: Thumbnail,
    @Embedded val comics: ItemContainer?
)
