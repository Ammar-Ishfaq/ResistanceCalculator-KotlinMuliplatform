package com.ammar.resistorassistant.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.ammar.resistorassistant.MR
import com.ammar.resistorassistant.screens.list.ListScreen
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

data object SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val navigateToListScreen by remember { mutableStateOf(false) }

        // Display SplashScreen
        SplashScreenContent(Modifier.fillMaxSize())

        // Delay for 5 seconds and navigate to ListScreen
        LaunchedEffect(key1 = navigateToListScreen) {
            delay(5000) // 5000 milliseconds = 5 seconds
            navigator.push(ListScreen)
        }
    }
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(MR.strings.splash_screen_text))
    }
}
