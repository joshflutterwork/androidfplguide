package com.fplguide.data.cache;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class FixtureCache_Factory implements Factory<FixtureCache> {
  @Override
  public FixtureCache get() {
    return newInstance();
  }

  public static FixtureCache_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FixtureCache newInstance() {
    return new FixtureCache();
  }

  private static final class InstanceHolder {
    static final FixtureCache_Factory INSTANCE = new FixtureCache_Factory();
  }
}
