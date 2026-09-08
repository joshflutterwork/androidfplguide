package com.fplguide.data.repository;

import com.fplguide.core.common.DispatcherProvider;
import com.fplguide.data.cache.FixtureCache;
import com.fplguide.data.remote.api.FplApi;
import com.fplguide.domain.repository.PlayerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class FixtureRepositoryImpl_Factory implements Factory<FixtureRepositoryImpl> {
  private final Provider<FplApi> apiProvider;

  private final Provider<FixtureCache> fixtureCacheProvider;

  private final Provider<PlayerRepository> playerRepositoryProvider;

  private final Provider<DispatcherProvider> dispatchersProvider;

  public FixtureRepositoryImpl_Factory(Provider<FplApi> apiProvider,
      Provider<FixtureCache> fixtureCacheProvider,
      Provider<PlayerRepository> playerRepositoryProvider,
      Provider<DispatcherProvider> dispatchersProvider) {
    this.apiProvider = apiProvider;
    this.fixtureCacheProvider = fixtureCacheProvider;
    this.playerRepositoryProvider = playerRepositoryProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public FixtureRepositoryImpl get() {
    return newInstance(apiProvider.get(), fixtureCacheProvider.get(), playerRepositoryProvider.get(), dispatchersProvider.get());
  }

  public static FixtureRepositoryImpl_Factory create(Provider<FplApi> apiProvider,
      Provider<FixtureCache> fixtureCacheProvider,
      Provider<PlayerRepository> playerRepositoryProvider,
      Provider<DispatcherProvider> dispatchersProvider) {
    return new FixtureRepositoryImpl_Factory(apiProvider, fixtureCacheProvider, playerRepositoryProvider, dispatchersProvider);
  }

  public static FixtureRepositoryImpl newInstance(FplApi api, FixtureCache fixtureCache,
      PlayerRepository playerRepository, DispatcherProvider dispatchers) {
    return new FixtureRepositoryImpl(api, fixtureCache, playerRepository, dispatchers);
  }
}
