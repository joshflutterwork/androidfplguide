package com.fplguide.di

import com.fplguide.BuildConfig
import com.fplguide.core.network.NetworkFactory
import com.fplguide.data.remote.api.FplApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = NetworkFactory.createJson()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        NetworkFactory.createOkHttpClient(debugLogging = BuildConfig.DEBUG)

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, client: OkHttpClient): Retrofit =
        NetworkFactory.createRetrofit(json, client, FplApi.BASE_URL)

    @Provides
    @Singleton
    fun provideFplApi(retrofit: Retrofit): FplApi = retrofit.create(FplApi::class.java)
}
