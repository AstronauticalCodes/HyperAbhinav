package com.nintherom.ui.navigation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "NavHostController"
/**
 * Navigation management system for the application that configures navigation routes,
 * animations, and deep links.
 *
 * This file provides:
 * - Central setup of the navigation graph
 * - Custom transition animations between screens
 * - Deep link handling for authentication
 * - Welcome screen persistence using preferences DataStore
 */

/**
 * DataStore instance for persisting preferences related to the welcome flow
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "welcome_prefs")

/**
 * Preference key for tracking whether welcome screen has been shown
 */
private val WELCOME_SHOWN_KEY = booleanPreferencesKey("welcome_shown")

/**
 * Determines if the welcome screen should be shown based on stored preferences
 *
 * @return True if the welcome screen has not been shown before, false otherwise
 */
private fun Context.shouldShowWelcome(): Boolean = runBlocking {
    dataStore.data.first()[WELCOME_SHOWN_KEY] != false
}

/**
 * Marks the welcome screen as shown in preferences to prevent showing it again
 */
private fun Context.setWelcomeShown() {
    CoroutineScope(Dispatchers.IO).launch {
        dataStore.edit { preferences ->
            preferences[WELCOME_SHOWN_KEY] = false
        }
    }
}

/**
 * Main navigation controller that sets up the application's navigation graph
 * with screen transitions, animations, and route handling.
 *
 * Features:
 * - Conditional navigation based on welcome screen status
 * - Custom slide and fade animations between screens
 * - Deep link support for authentication flows
 *
 * @param intent The Android intent that launched the application, used for deep link handling
 */
@Composable
fun NavHostController(intent: Intent) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val initialShowWelcome = remember { mutableStateOf(context.shouldShowWelcome()) }

    LaunchedEffect(initialShowWelcome.value) {
        if (initialShowWelcome.value) {
            context.setWelcomeShown()
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavDestinations.Chat,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, // New page slides in from the right
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(300)) // Ensures new page appears on top
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 4 }, // Moves left slightly (1/4th of screen width)
                animationSpec = tween(300)
            ) + scaleOut(
                targetScale = 0.92f, // Slight shrink before exit
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300)) // Optional fade effect
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth }, // Slide in from left (going back)
                animationSpec = tween(durationMillis = 500)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth / 4 }, // Moves right slightly (1/4 of screen width)
                animationSpec = tween(300)
            ) + scaleOut(
                targetScale = 0.92f,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {

    }
}
