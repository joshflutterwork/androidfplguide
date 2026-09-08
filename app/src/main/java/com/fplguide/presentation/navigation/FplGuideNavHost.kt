package com.fplguide.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.fplguide.core.designsystem.FplGreen
import com.fplguide.core.designsystem.FplOnGreen
import com.fplguide.core.designsystem.FplPurpleSurfaceDark
import com.fplguide.core.designsystem.FplTextDim
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fplguide.presentation.matchday.MatchdayScreen
import com.fplguide.presentation.playerdetail.PlayerDetailScreen
import com.fplguide.presentation.players.PlayersScreen

/** Route constants live next to the nav graph so arguments can't drift from routes. */
object Routes {
    const val MATCHDAY = "matchday"
    const val PLAYERS = "players"
    const val PLAYER_DETAIL = "players/{playerId}"
    const val ARG_PLAYER_ID = "playerId"

    fun playerDetail(playerId: Int): String = "players/$playerId"
}

/** Bottom-bar tabs. Only these routes show the bar and get state-preserving navigation. */
private data class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val label: String,
)

private val topLevelDestinations = listOf(
    TopLevelDestination(Routes.MATCHDAY, Icons.Filled.SportsSoccer, "Matchday"),
    TopLevelDestination(Routes.PLAYERS, Icons.Filled.People, "Players"),
)

@Composable
fun FplGuideNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in topLevelDestinations.map { it.route }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        // Screens handle their own status-bar padding; the bar owns the bottom inset.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = FplPurpleSurfaceDark,
                ) {
                    topLevelDestinations.forEach { destination ->
                        val selected = currentRoute == destination.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) navigateToTopLevel(navController, destination.route)
                            },
                            icon = {
                                Icon(destination.icon, contentDescription = destination.label)
                            },
                            label = { Text(destination.label) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = FplGreen,
                                selectedIconColor = FplOnGreen,
                                selectedTextColor = FplGreen,
                                unselectedIconColor = FplTextDim,
                                unselectedTextColor = FplTextDim,
                            ),
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.MATCHDAY,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        ) {
            composable(Routes.MATCHDAY) {
                MatchdayScreen()
            }
            composable(Routes.PLAYERS) {
                PlayersScreen(
                    onPlayerClick = { playerId -> navController.navigate(Routes.playerDetail(playerId)) },
                )
            }
            composable(
                route = Routes.PLAYER_DETAIL,
                arguments = listOf(navArgument(Routes.ARG_PLAYER_ID) { type = NavType.IntType }),
            ) {
                PlayerDetailScreen(
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}

/**
 * Canonical top-level navigation: single instance, saved/restored per tab. Tab state
 * (scroll position, filters, loaded data) survives switching — combined with the
 * ViewModels' load-once guards, revisiting a tab never re-fetches.
 */
private fun navigateToTopLevel(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
