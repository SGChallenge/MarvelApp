package com.example.marvelchallenge.domain.usecases

import androidx.paging.PagingData
import com.example.marvelchallenge.domain.model.CharacterPaged
import com.example.marvelchallenge.domain.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow

interface CharactersListUseCase {
    fun fetchCharacters(): Flow<PagingData<CharacterPaged>>
}

class CharactersListUseCaseImpl(private val repo: CharactersRepository) : CharactersListUseCase {
    override fun fetchCharacters(): Flow<PagingData<CharacterPaged>> = repo.fetchCharacters()
}
