package com.fplguide.di

import com.fplguide.data.repository.FixtureRepositoryImpl
import com.fplguide.data.repository.PlayerRepositoryImpl
import com.fplguide.domain.repository.FixtureRepository
import com.fplguide.domain.repository.PlayerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(impl: PlayerRepositoryImpl): PlayerRepository

    @Binds
    @Singleton
    abstract fun bindFixtureRepository(impl: FixtureRepositoryImpl): FixtureRepository
}
