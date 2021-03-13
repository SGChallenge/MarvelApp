package com.example.marvelchallenge.domain.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelchallenge.data.dao.MarvelHeroesDao
import com.example.marvelchallenge.data.networking.MarvelService
import com.example.marvelchallenge.domain.model.CharacterPaged
import com.example.marvelchallenge.utils.CharactersRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharactersRepository @Inject constructor(
    private val marvelService: MarvelService,
    private val dao: MarvelHeroesDao
) {
    fun fetchCharacters(): Flow<PagingData<CharacterPaged>> =
        Pager(
            PagingConfig(15, prefetchDistance = 5, initialLoadSize = 15),
            remoteMediator = CharactersRemoteMediator(dao, marvelService)
        ) {
            dao.loadPaged()
        }.flow

    fun loadCharacterById(id: Int) = dao.loadById(id)
}
