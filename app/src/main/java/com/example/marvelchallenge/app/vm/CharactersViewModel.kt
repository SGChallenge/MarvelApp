package com.example.marvelchallenge.app.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelchallenge.domain.model.CharacterPaged
import com.example.marvelchallenge.domain.usecases.CharactersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(useCase: CharactersListUseCase) : ViewModel() {

    val characterList: Flow<PagingData<CharacterPaged>> =
        useCase.fetchCharacters().cachedIn(viewModelScope)
}
