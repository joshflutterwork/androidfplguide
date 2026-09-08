package com.fplguide.presentation.playerdetail;

import androidx.lifecycle.SavedStateHandle;
import com.fplguide.domain.usecase.GetPlayerDetailUseCase;
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
public final class PlayerDetailViewModel_Factory implements Factory<PlayerDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<GetPlayerDetailUseCase> getPlayerDetailProvider;

  public PlayerDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetPlayerDetailUseCase> getPlayerDetailProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.getPlayerDetailProvider = getPlayerDetailProvider;
  }

  @Override
  public PlayerDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), getPlayerDetailProvider.get());
  }

  public static PlayerDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetPlayerDetailUseCase> getPlayerDetailProvider) {
    return new PlayerDetailViewModel_Factory(savedStateHandleProvider, getPlayerDetailProvider);
  }

  public static PlayerDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      GetPlayerDetailUseCase getPlayerDetail) {
    return new PlayerDetailViewModel(savedStateHandle, getPlayerDetail);
  }
}
