package com.fplguide.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class FilterPlayersUseCase_Factory implements Factory<FilterPlayersUseCase> {
  @Override
  public FilterPlayersUseCase get() {
    return newInstance();
  }

  public static FilterPlayersUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FilterPlayersUseCase newInstance() {
    return new FilterPlayersUseCase();
  }

  private static final class InstanceHolder {
    static final FilterPlayersUseCase_Factory INSTANCE = new FilterPlayersUseCase_Factory();
  }
}
