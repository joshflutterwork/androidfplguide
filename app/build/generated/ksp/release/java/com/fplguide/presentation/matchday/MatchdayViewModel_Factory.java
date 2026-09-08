package com.fplguide.presentation.matchday;

import com.fplguide.domain.usecase.GetCurrentGameweekUseCase;
import com.fplguide.domain.usecase.GetFixturesUseCase;
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
public final class MatchdayViewModel_Factory implements Factory<MatchdayViewModel> {
  private final Provider<GetFixturesUseCase> getFixturesProvider;

  private final Provider<GetCurrentGameweekUseCase> getCurrentGameweekProvider;

  public MatchdayViewModel_Factory(Provider<GetFixturesUseCase> getFixturesProvider,
      Provider<GetCurrentGameweekUseCase> getCurrentGameweekProvider) {
    this.getFixturesProvider = getFixturesProvider;
    this.getCurrentGameweekProvider = getCurrentGameweekProvider;
  }

  @Override
  public MatchdayViewModel get() {
    return newInstance(getFixturesProvider.get(), getCurrentGameweekProvider.get());
  }

  public static MatchdayViewModel_Factory create(Provider<GetFixturesUseCase> getFixturesProvider,
      Provider<GetCurrentGameweekUseCase> getCurrentGameweekProvider) {
    return new MatchdayViewModel_Factory(getFixturesProvider, getCurrentGameweekProvider);
  }

  public static MatchdayViewModel newInstance(GetFixturesUseCase getFixtures,
      GetCurrentGameweekUseCase getCurrentGameweek) {
    return new MatchdayViewModel(getFixtures, getCurrentGameweek);
  }
}
