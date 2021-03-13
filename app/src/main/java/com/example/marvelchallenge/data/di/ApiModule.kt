package com.example.marvelchallenge.data.di

import com.example.marvelchallenge.data.networking.FlowCallAdapterFactory
import com.example.marvelchallenge.data.networking.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideMarvelService(): MarvelService =
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
            .create(MarvelService::class.java)
}
