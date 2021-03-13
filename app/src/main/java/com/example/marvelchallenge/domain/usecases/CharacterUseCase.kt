package com.example.marvelchallenge.domain.usecases

import androidx.lifecycle.LiveData
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.repositories.CharactersRepository

interface CharacterUseCase {
    fun loadCharacterById(id: Int): LiveData<Character>
}

class CharacterUseCaseImpl(private val repo: CharactersRepository) : CharacterUseCase {
    override fun loadCharacterById(id: Int): LiveData<Character> = repo.loadCharacterById(id)
}
