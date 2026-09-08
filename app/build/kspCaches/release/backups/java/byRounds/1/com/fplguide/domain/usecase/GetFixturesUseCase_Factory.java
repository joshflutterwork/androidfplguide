package com.fplguide.domain.usecase;

import com.fplguide.domain.repository.FixtureRepository;
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
public final class GetFixturesUseCase_Factory implements Factory<GetFixturesUseCase> {
  private final Provider<FixtureRepository> repositoryProvider;

  public GetFixturesUseCase_Factory(Provider<FixtureRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFixturesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFixturesUseCase_Factory create(Provider<FixtureRepository> repositoryProvider) {
    return new GetFixturesUseCase_Factory(repositoryProvider);
  }

  public static GetFixturesUseCase newInstance(FixtureRepository repository) {
    return new GetFixturesUseCase(repository);
  }
}
