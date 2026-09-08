package com.fplguide.domain.usecase;

import com.fplguide.domain.repository.PlayerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class GetTeamsUseCase_Factory implements Factory<GetTeamsUseCase> {
  private final Provider<PlayerRepository> repositoryProvider;

  public GetTeamsUseCase_Factory(Provider<PlayerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTeamsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTeamsUseCase_Factory create(Provider<PlayerRepository> repositoryProvider) {
    return new GetTeamsUseCase_Factory(repositoryProvider);
  }

  public static GetTeamsUseCase newInstance(PlayerRepository repository) {
    return new GetTeamsUseCase(repository);
  }
}
