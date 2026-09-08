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
public final class GetPlayersUseCase_Factory implements Factory<GetPlayersUseCase> {
  private final Provider<PlayerRepository> repositoryProvider;

  public GetPlayersUseCase_Factory(Provider<PlayerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetPlayersUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetPlayersUseCase_Factory create(Provider<PlayerRepository> repositoryProvider) {
    return new GetPlayersUseCase_Factory(repositoryProvider);
  }

  public static GetPlayersUseCase newInstance(PlayerRepository repository) {
    return new GetPlayersUseCase(repository);
  }
}
