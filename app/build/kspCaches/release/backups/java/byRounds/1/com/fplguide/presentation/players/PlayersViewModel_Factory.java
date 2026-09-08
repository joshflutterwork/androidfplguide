package com.fplguide.presentation.players;

import com.fplguide.core.common.DispatcherProvider;
import com.fplguide.domain.usecase.FilterPlayersUseCase;
import com.fplguide.domain.usecase.GetCurrentGameweekUseCase;
import com.fplguide.domain.usecase.GetPlayersUseCase;
import com.fplguide.domain.usecase.GetTeamsUseCase;
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
public final class PlayersViewModel_Factory implements Factory<PlayersViewModel> {
  private final Provider<GetPlayersUseCase> getPlayersProvider;

  private final Provider<GetTeamsUseCase> getTeamsProvider;

  private final Provider<GetCurrentGameweekUseCase> getCurrentGameweekProvider;

  private final Provider<FilterPlayersUseCase> filterPlayersProvider;

  private final Provider<DispatcherProvider> dispatchersProvider;

  public PlayersViewModel_Factory(Provider<GetPlayersUseCase> getPlayersProvider,
      Provider<GetTeamsUseCase> getTeamsProvider,
      Provider<GetCurrentGameweekUseCase> getCurrentGameweekProvider,
      Provider<FilterPlayersUseCase> filterPlayersProvider,
      Provider<DispatcherProvider> dispatchersProvider) {
    this.getPlayersProvider = getPlayersProvider;
    this.getTeamsProvider = getTeamsProvider;
    this.getCurrentGameweekProvider = getCurrentGameweekProvider;
    this.filterPlayersProvider = filterPlayersProvider;
    this.dispatchersProvider = dispatchersProvider;
  }

  @Override
  public PlayersViewModel get() {
    return newInstance(getPlayersProvider.get(), getTeamsProvider.get(), getCurrentGameweekProvider.get(), filterPlayersProvider.get(), dispatchersProvider.get());
  }

  public static PlayersViewModel_Factory create(Provider<GetPlayersUseCase> getPlayersProvider,
      Provider<GetTeamsUseCase> getTeamsProvider,
      Provider<GetCurrentGameweekUseCase> getCurrentGameweekProvider,
      Provider<FilterPlayersUseCase> filterPlayersProvider,
      Provider<DispatcherProvider> dispatchersProvider) {
    return new PlayersViewModel_Factory(getPlayersProvider, getTeamsProvider, getCurrentGameweekProvider, filterPlayersProvider, dispatchersProvider);
  }

  public static PlayersViewModel newInstance(GetPlayersUseCase getPlayers, GetTeamsUseCase getTeams,
      GetCurrentGameweekUseCase getCurrentGameweek, FilterPlayersUseCase filterPlayers,
      DispatcherProvider dispatchers) {
    return new PlayersViewModel(getPlayers, getTeams, getCurrentGameweek, filterPlayers, dispatchers);
  }
}
