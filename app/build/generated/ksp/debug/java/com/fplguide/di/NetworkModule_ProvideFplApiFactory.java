package com.fplguide.di;

import com.fplguide.data.remote.api.FplApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideFplApiFactory implements Factory<FplApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideFplApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public FplApi get() {
    return provideFplApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideFplApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideFplApiFactory(retrofitProvider);
  }

  public static FplApi provideFplApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideFplApi(retrofit));
  }
}
