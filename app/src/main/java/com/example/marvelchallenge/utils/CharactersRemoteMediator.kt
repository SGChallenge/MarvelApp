package com.example.marvelchallenge.utils

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.data.dao.MarvelHeroesDao
import com.example.marvelchallenge.data.networking.MarvelService
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.model.CharacterPaged
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    val dao: MarvelHeroesDao,
    private val service: MarvelService,
) : RemoteMediator<Int, CharacterPaged>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterPaged>,
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> state.lastItemOrNull()?.page?.plus(1) ?: 0
        }

        return withContext(Dispatchers.IO) {
            val ts = System.currentTimeMillis()
            val response: List<Character> =
                service.getPagedCharacters(
                    ts,
                    page * 15,
                    (ts.toString() + BuildConfig.PRIVATE_API_KEY + BuildConfig.API_KEY).toMD5()
                ).data.results

            if (response.isNotEmpty()) {

                val characters: List<Character> = response

                dao.saveAll(characters, page)

                if (response.isEmpty()) {
                    MediatorResult.Error(Exception("0 elements"))
                } else {
                    MediatorResult.Success(response.size < state.config.pageSize)
                }
            } else {
                MediatorResult.Error(Exception("Characters response null"))
            }
        }
    }
}
