package com.example.marvelchallenge.app.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.marvelchallenge.domain.model.Character
import com.example.marvelchallenge.domain.usecases.CharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(useCase: CharacterUseCase) : ViewModel() {
    private val characterId: MutableLiveData<Int> = MutableLiveData()

    val character: LiveData<Character> = characterId.switchMap {
        useCase.loadCharacterById(it)
    }

    fun setCharacterId(id: Int) {
        characterId.value = id
    }
}
