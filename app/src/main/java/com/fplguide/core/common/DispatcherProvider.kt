package com.fplguide.core.common

import kotlinx.coroutines.CoroutineDispatcher

/** Injected so unit tests can swap in a test dispatcher. */
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
