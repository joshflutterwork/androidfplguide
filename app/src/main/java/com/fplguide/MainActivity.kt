package com.fplguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.graphics.Color as AndroidColor
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fplguide.core.designsystem.FplGuideTheme
import com.fplguide.presentation.navigation.FplGuideNavHost
import dagger.hilt.android.AndroidEntryPoint

/**
 * The app's only activity. Everything else is Compose destinations inside [FplGuideNavHost].
 *
 * The manifest theme is the splash theme, so [installSplashScreen] must run before the
 * content is set; it swaps in [R.style.Theme_FplGuide] as soon as the first frame is ready.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // The app is dark-only, so both bars must draw light icons regardless of the
        // system setting; the default enableEdgeToEdge() picks icon colour from it.
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
        )
        setContent {
            FplGuideTheme {
                FplGuideNavHost()
            }
        }
    }
}
