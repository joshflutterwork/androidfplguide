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
public final class ElementSummaryCache_Factory implements Factory<ElementSummaryCache> {
  @Override
  public ElementSummaryCache get() {
    return newInstance();
  }

  public static ElementSummaryCache_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ElementSummaryCache newInstance() {
    return new ElementSummaryCache();
  }

  private static final class InstanceHolder {
    static final ElementSummaryCache_Factory INSTANCE = new ElementSummaryCache_Factory();
  }
}
