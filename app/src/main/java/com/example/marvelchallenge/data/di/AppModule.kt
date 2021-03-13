package com.example.marvelchallenge.data.di

import com.example.marvelchallenge.domain.repositories.CharactersRepository
import com.example.marvelchallenge.domain.repositories.EventsRepository
import com.example.marvelchallenge.domain.usecases.CharacterUseCase
import com.example.marvelchallenge.domain.usecases.CharacterUseCaseImpl
import com.example.marvelchallenge.domain.usecases.CharactersListUseCase
import com.example.marvelchallenge.domain.usecases.CharactersListUseCaseImpl
import com.example.marvelchallenge.domain.usecases.EventsListUseCase
import com.example.marvelchallenge.domain.usecases.EventsListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCharactersListUseCase(
        repository: CharactersRepository
    ): CharactersListUseCase = CharactersListUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideEventsListUseCase(
        repository: EventsRepository
    ): EventsListUseCase = EventsListUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideCharacterUseCase(
        repository: CharactersRepository
    ): CharacterUseCase = CharacterUseCaseImpl(repository)
}
