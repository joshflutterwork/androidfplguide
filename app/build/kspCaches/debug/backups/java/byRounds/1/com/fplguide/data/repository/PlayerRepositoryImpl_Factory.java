package com.fplguide.data.repository;

import com.fplguide.core.common.DispatcherProvider;
import com.fplguide.data.cache.BootstrapCache;
import com.fplguide.data.cache.ElementSummaryCache;
import com.fplguide.data.remote.api.FplApi;
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
public final class PlayerRepositoryImpl_Factory implements Factory<PlayerRepositoryImpl> {
  private final Provider<FplApi> apiProvider;

  private final Provider<BootstrapCache> bootstrapCacheProvider;

  private final Provider<ElementSummaryCache> elementSummaryCacheProvider;

  private final Provider<DispatcherProvider> dispatchersProvider;

  public PlayerRepositoryImpl_Factory(Provider<FplApi> apiProvider,
      Provider<BootstrapCache> bootstrapCacheProvider,
      Provider<ElementSummaryCache> elementSummaryCacheProvider,
      Provider<DispatcherProvider> dispatchersProvider) {
    this.apiProvider = apiProvider;
    this.bootstrapCacheProvider = bootstrapCacheProvider;
    this.elementSummaryCacheProvider = elementSummaryCacheProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public PlayerRepositoryImpl get() {
    return newInstance(apiProvider.get(), bootstrapCacheProvider.get(), elementSummaryCacheProvider.get(), dispatchersProvider.get());
  }

  public static PlayerRepositoryImpl_Factory create(Provider<FplApi> apiProvider,
      Provider<BootstrapCache> bootstrapCacheProvider,
      Provider<ElementSummaryCache> elementSummaryCacheProvider,
      Provider<DispatcherProvider> dispatchersProvider) {
    return new PlayerRepositoryImpl_Factory(apiProvider, bootstrapCacheProvider, elementSummaryCacheProvider, dispatchersProvider);
  }

  public static PlayerRepositoryImpl newInstance(FplApi api, BootstrapCache bootstrapCache,
      ElementSummaryCache elementSummaryCache, DispatcherProvider dispatchers) {
    return new PlayerRepositoryImpl(api, bootstrapCache, elementSummaryCache, dispatchers);
  }
}
