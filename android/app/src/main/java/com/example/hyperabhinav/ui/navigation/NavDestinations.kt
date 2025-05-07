/*
 *
 *  * Copyright © 2024
 *  * Project: Nintherom
 *  * Module: Nintherom.app.main
 *  * File: NavDestinations.kt
 *  * Path: C:/Source/Nintherom/android/app/src/main/java/com/nintherom/ui/commons/NavDestinations.kt
 *  * Class: NavDestinations.kt
 *  * Qualified Class: C:/Source/Nintherom/android/app/src/main/java/com/nintherom/ui/commons/NavDestinations.kt
 *  * Last Modified: 2024-12-23 21:58:57
 *  *
 *  * This file is part of the Nintherom project.
 *  * Unauthorized copying of this file, via any medium is strictly prohibited.
 *  * Proprietary and confidential.
 *
 */

package com.nintherom.ui.navigation

import kotlinx.serialization.Serializable


/**
 * Defines all navigation destinations within the application.
 *
 * This sealed class hierarchy provides type-safe navigation routes for the application's
 * navigation graph. Each destination is serializable to support navigation arguments and
 * deep linking.
 */
sealed class NavDestinations {
    @Serializable
    object Chat: NavDestinations()
}
