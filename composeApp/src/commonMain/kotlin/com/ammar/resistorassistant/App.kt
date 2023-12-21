package com.ammar.resistorassistant

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.ammar.resistorassistant.screens.SplashScreen
import com.ammar.resistorassistant.screens.list.ListScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(SplashScreen)
    }
}
