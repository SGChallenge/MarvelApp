package com.example.marvelchallenge.data.di

import com.example.marvelchallenge.BuildConfig
import com.example.marvelchallenge.data.networking.FlowCallAdapterFactory
import com.example.marvelchallenge.data.networking.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
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
            .client(
                OkHttpClient.Builder().addInterceptor(
                    Interceptor {
                        val original: Request = it.request()
                        val originalUrl: HttpUrl = original.url
                        val url: HttpUrl = originalUrl.newBuilder()
                            .addQueryParameter("apikey", BuildConfig.API_KEY)
                            .build()

                        val requestBuilder: Request.Builder = original.newBuilder().url(url)
                        val request: Request = requestBuilder.build()
                        it.proceed(request)
                    }
                ).build()
            )
            .build()
            .create(MarvelService::class.java)
}
