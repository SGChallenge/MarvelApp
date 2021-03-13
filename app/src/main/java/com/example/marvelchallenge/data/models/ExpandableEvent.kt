package com.example.marvelchallenge.data.models

import com.example.marvelchallenge.domain.model.ItemContainer
import com.example.marvelchallenge.domain.model.Thumbnail

data class ExpandableEvent(
    val id: String,
    val title: String,
    val start: String?,
    val end: String?,
    val thumbnail: Thumbnail,
    val comics: ItemContainer?,
    var isExpanded: Boolean
)
