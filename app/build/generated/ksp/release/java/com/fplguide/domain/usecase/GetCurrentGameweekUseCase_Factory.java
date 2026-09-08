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
public final class GetCurrentGameweekUseCase_Factory implements Factory<GetCurrentGameweekUseCase> {
  private final Provider<PlayerRepository> repositoryProvider;

  public GetCurrentGameweekUseCase_Factory(Provider<PlayerRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetCurrentGameweekUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetCurrentGameweekUseCase_Factory create(
      Provider<PlayerRepository> repositoryProvider) {
    return new GetCurrentGameweekUseCase_Factory(repositoryProvider);
  }

  public static GetCurrentGameweekUseCase newInstance(PlayerRepository repository) {
    return new GetCurrentGameweekUseCase(repository);
  }
}
