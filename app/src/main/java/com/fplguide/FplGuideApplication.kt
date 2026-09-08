package com.fplguide

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point. Hilt generates its component here; nothing else belongs
 * in this class — initialization logic goes into injected lazily-initialized singletons
 * so unit tests never pay for it.
 */
@HiltAndroidApp
class FplGuideApplication : Application()
